package com.ysumma.job.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    private String ruc;
    private String name;
    private String taxpayerStatus;
    private String residenceCondition;
    private String location;
    private String roadType;
    private String streetName;
    private String zoneCode;
    private String zoneType;
    private String number;
    private String interior;
    private String lot;
    private String department;
    private String apple;
    private String kilometer;

    public Company(){
        super();
    }

    public Company(String ruc, String name, String taxpayerStatus, String residenceCondition, String location, String roadType, String streetName, String zoneCode, String zoneType, String number, String interior, String lot, String department, String apple, String kilometer) {
        super();
        this.ruc = ruc;
        this.name = name;
        this.taxpayerStatus = taxpayerStatus;
        this.residenceCondition = residenceCondition;
        this.location = location;
        this.roadType = roadType;
        this.streetName = streetName;
        this.zoneCode = zoneCode;
        this.zoneType = zoneType;
        this.number = number;
        this.interior = interior;
        this.lot = lot;
        this.department = department;
        this.apple = apple;
        this.kilometer = kilometer;
    }

}