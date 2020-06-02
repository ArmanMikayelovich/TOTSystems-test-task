package ru.totsystems.moexiss.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "histories")
@Getter
@Setter
public class HistoryEntity {
    private static final String[] FIELDS;

    static {
        Field[] declaredFields = HistoryEntity.class.getDeclaredFields();
        FIELDS = new String[declaredFields.length - 2];
        for (int x = 0; x < FIELDS.length; x++) {
            FIELDS[x] = declaredFields[x + 2].getName();
        }
    }

    public static String[] getFIELDS() {
        return Arrays.copyOf(FIELDS, FIELDS.length);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String BOARDID;
    private LocalDate TRADEDATE;
    private String SHORTNAME;

    @ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinColumn(name = "SECID",updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SecurityEntity SECID;

    private Double NUMTRADES;
    private Double VALUE;
    private Double OPEN;
    private Double LOW;
    private Double HIGH;
    private Double LEGALCLOSEPRICE;
    private Double WAPRICE;
    private Double CLOSE;
    private Double VOLUME;
    private Double MARKETPRICE2;
    private Double MARKETPRICE3;
    private Double ADMITTEDQUOTE;

    private Double MP2VALTRD;
    private Double MARKETPRICE3TRADESVALUE;
    private Double ADMITTEDVALUE;
    private Double WAVAL;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryEntity historyEntity = (HistoryEntity) o;
        return Double.compare(historyEntity.NUMTRADES, NUMTRADES) == 0 &&
                Double.compare(historyEntity.VALUE, VALUE) == 0 &&
                Double.compare(historyEntity.OPEN, OPEN) == 0 &&
                Double.compare(historyEntity.LOW, LOW) == 0 &&
                Double.compare(historyEntity.HIGH, HIGH) == 0 &&
                Double.compare(historyEntity.LEGALCLOSEPRICE, LEGALCLOSEPRICE) == 0 &&
                Double.compare(historyEntity.WAPRICE, WAPRICE) == 0 &&
                Double.compare(historyEntity.CLOSE, CLOSE) == 0 &&
                Double.compare(historyEntity.VOLUME, VOLUME) == 0 &&
                Double.compare(historyEntity.MARKETPRICE2, MARKETPRICE2) == 0 &&
                Double.compare(historyEntity.MARKETPRICE3, MARKETPRICE3) == 0 &&
                Double.compare(historyEntity.ADMITTEDQUOTE, ADMITTEDQUOTE) == 0 &&
                Double.compare(historyEntity.MP2VALTRD, MP2VALTRD) == 0 &&
                Double.compare(historyEntity.MARKETPRICE3TRADESVALUE, MARKETPRICE3TRADESVALUE) == 0 &&
                Double.compare(historyEntity.ADMITTEDVALUE, ADMITTEDVALUE) == 0 &&
                Double.compare(historyEntity.WAVAL, WAVAL) == 0 &&
                Objects.equals(BOARDID, historyEntity.BOARDID) &&
                Objects.equals(TRADEDATE, historyEntity.TRADEDATE) &&
                Objects.equals(SHORTNAME, historyEntity.SHORTNAME) &&
                Objects.equals(SECID, historyEntity.SECID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, BOARDID, TRADEDATE, SHORTNAME, SECID, NUMTRADES, VALUE, OPEN, LOW, HIGH, LEGALCLOSEPRICE, WAPRICE, CLOSE, VOLUME, MARKETPRICE2, MARKETPRICE3, ADMITTEDQUOTE, MP2VALTRD, MARKETPRICE3TRADESVALUE, ADMITTEDVALUE, WAVAL);
    }

    @Override
    public String toString() {
        return "\nHistory{" +
                "BOARDID='" + BOARDID + '\'' +
                ", TRADEDATE='" + TRADEDATE + '\'' +
                ", SHORTNAME='" + SHORTNAME + '\'' +
                ", SECID='" + SECID + '\'' +
                ", NUMTRADES=" + NUMTRADES +
                ", VALUE=" + VALUE +
                ", OPEN=" + OPEN +
                ", LOW=" + LOW +
                ", HIGH=" + HIGH +
                ", LEGALCLOSEPRICE=" + LEGALCLOSEPRICE +
                ", WAPRICE=" + WAPRICE +
                ", CLOSE=" + CLOSE +
                ", VOLUME=" + VOLUME +
                ", MARKETPRICE2=" + MARKETPRICE2 +
                ", MARKETPRICE3=" + MARKETPRICE3 +
                ", ADMITTEDQUOTE=" + ADMITTEDQUOTE +
                ", MP2VALTRD=" + MP2VALTRD +
                ", MARKETPRICE3TRADESVALUE=" + MARKETPRICE3TRADESVALUE +
                ", ADMITTEDVALUE=" + ADMITTEDVALUE +
                ", WAVAL=" + WAVAL +
                '}';
    }
}
