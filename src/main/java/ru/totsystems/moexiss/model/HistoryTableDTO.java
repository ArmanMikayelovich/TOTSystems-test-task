package ru.totsystems.moexiss.model;

import lombok.Getter;

@Getter
public class HistoryTableDTO {

    public HistoryTableDTO(HistoryEntity historyEntity) {
        this.secid = historyEntity.getSECID().getSecid();
        this.regnumber = historyEntity.getSECID().getRegnumber();
        this.name = historyEntity.getSECID().getName();
        this.emitent_title = historyEntity.getSECID().getEmitent_title();
        this.tradedate = historyEntity.getTRADEDATE().toString();
        this.numtrades = historyEntity.getNUMTRADES();
        this.open = historyEntity.getOPEN();
        this.close = historyEntity.getCLOSE();
    }

    private String secid;
    private String regnumber;
    private String name;
    private String emitent_title;
    private String tradedate;
    private Double numtrades;
    private Double open;
    private Double close;

}
