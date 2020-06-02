package ru.totsystems.moexiss.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import ru.totsystems.moexiss.model.SecurityEntity;
import ru.totsystems.moexiss.repository.SecurityRepository;
import ru.totsystems.moexiss.service.xml.SecurityRowHandler;
import ru.totsystems.moexiss.util.exception.NameValidationException;
import ru.totsystems.moexiss.util.exception.SecurityAlreadyExists;
import ru.totsystems.moexiss.util.exception.SecurityNotFoundException;
import ru.totsystems.moexiss.util.exception.ServerSideException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class SecurityService {
    private static final String NAME_REGEX = "[а-яёА-ЯЁ0-9\\s.]+";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private static final String MOEX_SITE_SEARCH_SECID = "http://iss.moex.com/iss/securities.xml?q=";
    private final SecurityRepository securityRepository;

    @Transactional
    public void save(SecurityEntity securityEntity) {

        validateName(securityEntity.getName());

        Optional<SecurityEntity> securityOptional = securityRepository.findById(securityEntity.getSecid());
        if (securityOptional.isPresent()) {
            throw new SecurityAlreadyExists(securityEntity.getSecid());
        } else {
            securityRepository.save(securityEntity);
        }
    }

    @Transactional
    public SecurityEntity saveFromMOEX(String secid)  {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> forEntity;
            forEntity = restTemplate.getForEntity(MOEX_SITE_SEARCH_SECID + secid, String.class);
            InputStream stream = new ByteArrayInputStream(forEntity.getBody().getBytes());

            List<SecurityEntity> securityEntities = parseXML(stream);
            SecurityEntity security = securityEntities.stream()
                    .filter(securityEntity -> securityEntity.getSecid().equals(secid))
                    .collect(Collectors.toList())
                    .get(0);

            save(security);
            return security;
        } catch (Exception ex) {
            throw new ServerSideException();
        }
    }

    @Transactional
    public void saveImported(InputStream xmlStream) throws IOException, ParserConfigurationException, SAXException {
        ArrayList<SecurityEntity> securities = parseXML(xmlStream);
        securityRepository.saveAll(securities);
    }

    private ArrayList<SecurityEntity> parseXML(InputStream xmlStream) throws ParserConfigurationException, SAXException, IOException {
        ArrayList<SecurityEntity> securities = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        SecurityRowHandler securityRowHandler = new SecurityRowHandler(securities);
        saxParser.parse(xmlStream, securityRowHandler);
        return securities;
    }

    @Transactional
    public void update(SecurityEntity securityEntity) {

        validateName(securityEntity.getName());

        Optional<SecurityEntity> securityOptional = securityRepository.findById(securityEntity.getSecid());
        if (securityOptional.isPresent()) {
            securityRepository.save(securityEntity);
        } else {
            throw new SecurityNotFoundException(securityEntity.getSecid());
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public SecurityEntity get(String secid) {
        Optional<SecurityEntity> securityOptional = securityRepository.findById(secid);
        if (securityOptional.isPresent()) {
            return securityOptional.get();
        } else {
            throw new SecurityNotFoundException(secid);
        }
    }

    @Transactional
    public void remove(String secid) {
        Optional<SecurityEntity> securityOptional = securityRepository.findById(secid);
        if (securityOptional.isPresent()) {
            securityRepository.deleteById(secid);
        } else {
            throw new SecurityNotFoundException(secid);
        }
    }

    private void validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new NameValidationException();
        }
    }
}
