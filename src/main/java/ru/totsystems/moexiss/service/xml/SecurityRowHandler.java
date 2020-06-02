package ru.totsystems.moexiss.service.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.totsystems.moexiss.model.SecurityEntity;
import ru.totsystems.moexiss.util.exception.IncorrectArgumentException;

import java.lang.reflect.Field;
import java.util.List;

public class SecurityRowHandler extends DefaultHandler {

    private static final String ROW = "row";
    private static final String EMPTY_STRING_MSG = "empty String";
    private final List<SecurityEntity> securityEntityList;

    public SecurityRowHandler(List<SecurityEntity> securityEntityList) {
        this.securityEntityList = securityEntityList;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(ROW)) {
            SecurityEntity securityEntity = new SecurityEntity();
            String[] fields = SecurityEntity.getFIELDS();
            for (int x = 0; x < fields.length; x++) {
                try {
                    String attributeName = attributes.getQName(x);
                    Field field = securityEntity.getClass().getDeclaredField(attributeName);
                    field.setAccessible(true);
                    if (field.getType().equals(Long.class)) {
                        try {
                            long value = Long.parseLong(attributes.getValue(fields[x]));
                            field.set(securityEntity, value);
                        } catch (NumberFormatException ex) {
                            if (!EMPTY_STRING_MSG.equals(ex.getMessage())) {
                                throw new IncorrectArgumentException("Incorrect argument" +
                                        field.getName() + " : " + attributes.getValue(fields[x]));
                            }
                        }

                    } else {
                        field.set(securityEntity, attributes.getValue(fields[x]));
                    }
                    field.setAccessible(false);
                } catch (IllegalAccessException |
                        NoSuchFieldException |
                        IncorrectArgumentException e) {
                    return;
                }
            }
            securityEntityList.add(securityEntity);
        }

    }
}