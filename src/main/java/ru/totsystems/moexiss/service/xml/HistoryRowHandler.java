package ru.totsystems.moexiss.service.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.totsystems.moexiss.model.HistoryEntity;
import ru.totsystems.moexiss.model.SecurityEntity;
import ru.totsystems.moexiss.service.SecurityService;
import ru.totsystems.moexiss.util.exception.IncorrectArgumentException;
import ru.totsystems.moexiss.util.exception.SecurityNotFoundException;
import ru.totsystems.moexiss.util.exception.ServerSideException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

public class HistoryRowHandler extends DefaultHandler {
    private static final String DATA = "data";
    private static final String ID = "id";
    private static final String ROW = "row";
    private static final String HISTORY = "history";
    private static final String HISTORY_CURSOR = "history.cursor";
    private static final String EMPTY_STRING_MSG = "empty String";
    private final SecurityService securityService;

    private boolean isInHistory;
    private final List<HistoryEntity> historyEntityList;

    public HistoryRowHandler(List<HistoryEntity> historyEntityList, SecurityService securityService) {
        this.securityService = securityService;
        this.historyEntityList = historyEntityList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (DATA.equals(qName) && HISTORY.equals(attributes.getValue(ID))) {
            isInHistory = true;
        } else if (DATA.equals(qName) && HISTORY_CURSOR.equals(attributes.getValue(ID))) {
            isInHistory = false;
        }

        if (isInHistory && ROW.equals(qName)) {
            HistoryEntity historyEntity = new HistoryEntity();
            String[] fields = HistoryEntity.getFIELDS();
            for (int x = 0; x < fields.length; x++) {
                Field field = null;
                try {
                    String attributeName = attributes.getQName(x);
                     field = historyEntity.getClass().getDeclaredField(attributeName);
                    field.setAccessible(true);
                    if (field.getType().equals(Double.class)) {
                        try {
                            double value = Double.parseDouble(attributes.getValue(fields[x]));
                            field.set(historyEntity, value);
                        } catch (NumberFormatException ex) {
                            if (!EMPTY_STRING_MSG.equals(ex.getMessage())) {
                                throw new IncorrectArgumentException("Incorrent argument" +
                                        field.getName() + " : " + attributes.getValue(fields[x]));
                            }
                        }

                    } else if (field.getType().equals(SecurityEntity.class)) {
                        setSecurity(attributes.getValue(fields[x]), historyEntity, field);
                    } else if (field.getType().equals(LocalDate.class)) {
                        LocalDate value = LocalDate.parse(attributes.getValue(fields[x]));
                        field.set(historyEntity, value);
                    } else {
                        field.set(historyEntity, attributes.getValue(fields[x]));
                    }

                } catch (IllegalAccessException |
                        NoSuchFieldException |
                        ParserConfigurationException |
                        IOException |
                        ServerSideException e) {
                    return;
                } finally {
                    if (field != null) {
                        field.setAccessible(false);
                    }
                }
            }
            historyEntityList.add(historyEntity);
        }
    }

    private void setSecurity(String secid, HistoryEntity historyEntity, Field field)
            throws ParserConfigurationException, SAXException, IOException, IllegalAccessException {
        SecurityEntity securityEntity;
        try {
            securityEntity = securityService.get(secid);
        } catch (SecurityNotFoundException exception) {
            securityEntity = securityService.saveFromMOEX(secid);
        }
        field.set(historyEntity, securityEntity);
    }
}

