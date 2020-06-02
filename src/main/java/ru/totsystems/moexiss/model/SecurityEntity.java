package ru.totsystems.moexiss.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "securities")
@Getter
@Setter
public class SecurityEntity {

    private static final String[] FIELDS;
    static{
        Field[] declaredFields = SecurityEntity.class.getDeclaredFields();
        FIELDS = new String[declaredFields.length-1];
        for (int x = 0; x < FIELDS.length; x++) {
            FIELDS[x] = declaredFields[x+1].getName();
        }
    }

    public static String[] getFIELDS() {
        return Arrays.copyOf(FIELDS, FIELDS.length);
    }

    private Long id;

    @Id
    private String secid;

    private String shortname;
    private String regnumber;
    private String name;
    private String isin;
    private Long is_traded;
    private Long emitent_id;
    private String emitent_title;
    private String emitent_inn;
    private String emitent_okpo;
    private String gosreg;
    private String type;
    @Column(name = "`group`")
    private String group;
    private String primary_boardid;
    private String marketprice_boardid;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityEntity securityEntity = (SecurityEntity) o;
        return secid.equals(securityEntity.secid) &&
                Objects.equals(id, securityEntity.id) &&
                Objects.equals(shortname, securityEntity.shortname) &&
                Objects.equals(regnumber, securityEntity.regnumber) &&
                Objects.equals(name, securityEntity.name) &&
                Objects.equals(isin, securityEntity.isin) &&
                Objects.equals(is_traded, securityEntity.is_traded) &&
                Objects.equals(emitent_id, securityEntity.emitent_id) &&
                Objects.equals(emitent_title, securityEntity.emitent_title) &&
                Objects.equals(emitent_inn, securityEntity.emitent_inn) &&
                Objects.equals(emitent_okpo, securityEntity.emitent_okpo) &&
                Objects.equals(gosreg, securityEntity.gosreg) &&
                Objects.equals(type, securityEntity.type) &&
                Objects.equals(group, securityEntity.group) &&
                Objects.equals(primary_boardid, securityEntity.primary_boardid) &&
                Objects.equals(marketprice_boardid, securityEntity.marketprice_boardid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secid, id, shortname, regnumber, name, isin, is_traded, emitent_id, emitent_title, emitent_inn, emitent_okpo, gosreg, type, group, primary_boardid, marketprice_boardid);
    }

    @Override
    public String toString() {
        return "\nSecurity{" +
                "id='" + id + '\'' +
                ", secid='" + secid + '\'' +
                ", shortname='" + shortname + '\'' +
                ", regnumber='" + regnumber + '\'' +
                ", name='" + name + '\'' +
                ", isin='" + isin + '\'' +
                ", is_traded='" + is_traded + '\'' +
                ", emitent_id=" + emitent_id +
                ", emitent_title='" + emitent_title + '\'' +
                ", emitent_inn='" + emitent_inn + '\'' +
                ", emitent_okpo='" + emitent_okpo + '\'' +
                ", gosreg='" + gosreg + '\'' +
                ", type='" + type + '\'' +
                ", group='" + group + '\'' +
                ", primary_boardid='" + primary_boardid + '\'' +
                ", marketprice_boardid='" + marketprice_boardid + '\'' +
                '}';
    }
}