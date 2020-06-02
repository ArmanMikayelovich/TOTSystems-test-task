package ru.totsystems.moexiss.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import ru.totsystems.moexiss.model.HistoryDTO;
import ru.totsystems.moexiss.model.HistoryEntity;
import ru.totsystems.moexiss.model.HistoryTableDTO;
import ru.totsystems.moexiss.repository.HistoryRepository;
import ru.totsystems.moexiss.service.xml.HistoryRowHandler;
import ru.totsystems.moexiss.util.SortDTO;
import ru.totsystems.moexiss.util.exception.HistoryNotFoundException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {
    private static final String TRADE_DATE = "TRADEDATE";
    private static final String EMITENT_TITLE = "SECID.emitent_title";

    private final HistoryRepository historyRepository;
    private final SecurityService securityService;

    public HistoryEntity dtoToEntity(HistoryDTO dto) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(dto.getId());
        historyEntity.setBOARDID(dto.getBOARDID());


        historyEntity.setTRADEDATE(dto.getTRADEDATE());

        historyEntity.setSHORTNAME(dto.getSHORTNAME());

        historyEntity.setSECID(securityService.get(dto.getSECID()));

        historyEntity.setNUMTRADES(dto.getNUMTRADES());

        historyEntity.setVALUE(dto.getVALUE());

        historyEntity.setOPEN(dto.getOPEN());

        historyEntity.setLOW(dto.getLOW());
        historyEntity.setHIGH(dto.getHIGH());
        historyEntity.setLEGALCLOSEPRICE(dto.getLEGALCLOSEPRICE());
        historyEntity.setWAPRICE(dto.getWAPRICE());
        historyEntity.setCLOSE(dto.getCLOSE());
        historyEntity.setVOLUME(dto.getVOLUME());

        historyEntity.setMARKETPRICE2(dto.getMARKETPRICE2());
        historyEntity.setMARKETPRICE3(dto.getMARKETPRICE3());
        historyEntity.setADMITTEDQUOTE(dto.getADMITTEDQUOTE());
        historyEntity.setMP2VALTRD(dto.getMP2VALTRD());
        historyEntity.setMARKETPRICE3TRADESVALUE(dto.getMARKETPRICE3TRADESVALUE());
        historyEntity.setADMITTEDVALUE(dto.getADMITTEDVALUE());
        historyEntity.setWAVAL(dto.getWAVAL());
        return historyEntity;
    }

    public HistoryDTO entityToDto(HistoryEntity entity) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setId(entity.getId());
        historyDTO.setBOARDID(entity.getBOARDID());
        historyDTO.setTRADEDATE(entity.getTRADEDATE());
        historyDTO.setSHORTNAME(entity.getSHORTNAME());
        historyDTO.setSECID(entity.getSECID().getSecid());
        historyDTO.setNUMTRADES(entity.getNUMTRADES());
        historyDTO.setVALUE(entity.getVALUE());
        historyDTO.setOPEN(entity.getOPEN());
        historyDTO.setLOW(entity.getLOW());
        historyDTO.setHIGH(entity.getHIGH());
        historyDTO.setLEGALCLOSEPRICE(entity.getLEGALCLOSEPRICE());
        historyDTO.setWAPRICE(entity.getWAPRICE());
        historyDTO.setCLOSE(entity.getCLOSE());
        historyDTO.setVOLUME(entity.getVOLUME());
        historyDTO.setMARKETPRICE2(entity.getMARKETPRICE2());
        historyDTO.setMARKETPRICE3(entity.getMARKETPRICE3());
        historyDTO.setADMITTEDQUOTE(entity.getADMITTEDQUOTE());
        historyDTO.setMP2VALTRD(entity.getMP2VALTRD());
        historyDTO.setMARKETPRICE3TRADESVALUE(entity.getMARKETPRICE3TRADESVALUE());
        historyDTO.setADMITTEDVALUE(entity.getADMITTEDVALUE());
        historyDTO.setWAVAL(entity.getWAVAL());
        return historyDTO;
    }

    public List<HistoryTableDTO> getForTable(SortDTO sortDTO) {

        boolean isEmitentTitleValid = sortDTO.getEmitentTitle() != null && !sortDTO.getEmitentTitle().isEmpty();

        boolean isStartDateValid = sortDTO.getStartDate() != null &&
                sortDTO.getStartDate().isBefore(LocalDate.now().plusDays(1));

        boolean isEndDateValid = sortDTO.getEndDate() != null &&
                sortDTO.getEndDate().isBefore(LocalDate.now().plusDays(1))
                && sortDTO.getEndDate().isAfter(sortDTO.getStartDate().plusDays(1));

        boolean isDatesValid = isStartDateValid && isEndDateValid;

        List<HistoryEntity> historyEntities;

        if (isEmitentTitleValid && isDatesValid) {
            historyEntities = historyRepository.getAllForTable(sortDTO.getStartDate(), sortDTO.getEndDate(),
                    "%" + sortDTO.getEmitentTitle() + "%",
                    getSort(sortDTO)
            );

        } else if (isDatesValid) {
            historyEntities = historyRepository.getAllForTable(sortDTO.getStartDate(),
                    sortDTO.getEndDate(),
                    getSort(sortDTO));

        } else if (isEmitentTitleValid) {
            historyEntities = historyRepository.getAllForTable(
                    "%" + sortDTO.getEmitentTitle() + "%",
                    getSort(sortDTO));

        } else {
            historyEntities = historyRepository.getAllForTable(getSort(sortDTO));
        }

        return historyEntities.stream().map(HistoryTableDTO::new).collect(Collectors.toList());
    }

    private Sort getSort(SortDTO sortDTO) {
        boolean isSortDirectionValid = "ASC".equals(sortDTO.getSortDirection())
                || "DESC".equals(sortDTO.getSortDirection());
        boolean isSortAttributeValid = TRADE_DATE.equals(sortDTO.getSortAttribute()) ||
                EMITENT_TITLE.equals(sortDTO.getSortAttribute());

        if (isSortDirectionValid && isSortAttributeValid) {
            return Sort.by(
                    Sort.Direction.fromString(sortDTO.getSortDirection()),
                    sortDTO.getSortAttribute());
        }

        return Sort.unsorted();

    }

    public HistoryDTO getById(Long id) {
        HistoryEntity historyEntity = historyRepository.findById(id).orElseThrow(HistoryNotFoundException::new);
        return entityToDto(historyEntity);
    }

    @Transactional
    public void save(HistoryDTO historyDTO) {
        HistoryEntity historyEntity = dtoToEntity(historyDTO);
        historyRepository.save(historyEntity);
    }

    @Transactional
    public void update(HistoryDTO historyDTO) {
        if (historyDTO.getId() == null) {
            throw new HistoryNotFoundException();
        }

        boolean isPresent = historyRepository.findById(historyDTO.getId()).isPresent();
        if (!isPresent) {
            throw new HistoryNotFoundException(historyDTO.getId() + "");
        }

        save(historyDTO);
    }

    @Transactional
    public void delete(Long id) {
        boolean isPresent = historyRepository.findById(id).isPresent();
        if (isPresent) {
            historyRepository.deleteById(id);
        } else {
            throw new HistoryNotFoundException(id + "");
        }
    }

    @Transactional
    public void saveImported(MultipartFile file) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        List<HistoryEntity> historyEntityList = new ArrayList<>();
        SAXParser saxParser = factory.newSAXParser();
        HistoryRowHandler historyRowHandler = new HistoryRowHandler(historyEntityList, securityService);
        saxParser.parse(file.getInputStream(), historyRowHandler);
        historyRepository.saveAll(historyEntityList);
    }
}
