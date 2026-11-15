package com.medicine.donation;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Medicine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    private int quantity;
    private LocalDate expiryDate;
    private String donorName;
    private String batchNumber;

    public Medicine() {}
    public Medicine(String name, String unit, int quantity, LocalDate expiryDate, String donorName, String batchNumber) {
        this.name=name; this.unit=unit; this.quantity=quantity; this.expiryDate=expiryDate;
        this.donorName=donorName; this.batchNumber=batchNumber;
    }
    public String getExpiryStatus() {
        if (expiryDate == null) return "UNKNOWN";
        if (expiryDate.isBefore(LocalDate.now())) return "EXPIRED";
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate) <= 30 ? "EXPIRING_SOON" : "VALID";
    }
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getUnit(){return unit;} public void setUnit(String v){unit=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
    public LocalDate getExpiryDate(){return expiryDate;} public void setExpiryDate(LocalDate v){expiryDate=v;}
    public String getDonorName(){return donorName;} public void setDonorName(String v){donorName=v;}
    public String getBatchNumber(){return batchNumber;} public void setBatchNumber(String v){batchNumber=v;}
}
