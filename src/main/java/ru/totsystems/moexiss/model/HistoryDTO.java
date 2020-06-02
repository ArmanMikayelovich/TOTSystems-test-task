package ru.totsystems.moexiss.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
@Data
public class HistoryDTO {
    private Long id;
    private String BOARDID;
    private LocalDate TRADEDATE;
    private String SHORTNAME;
    private String SECID;
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

}
