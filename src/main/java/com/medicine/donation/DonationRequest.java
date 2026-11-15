package com.medicine.donation;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="donation_requests")
public class DonationRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String recipientName;
    private String medicineName;
    private int quantity;
    private String status = "PENDING";
    private LocalDate requestDate = LocalDate.now();

    public DonationRequest() {}
    public DonationRequest(String recipientName, String medicineName, int quantity) {
        this.recipientName=recipientName; this.medicineName=medicineName; this.quantity=quantity;
    }
    public Long getId(){return id;} public String getRecipientName(){return recipientName;}
    public void setRecipientName(String v){recipientName=v;}
    public String getMedicineName(){return medicineName;} public void setMedicineName(String v){medicineName=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public LocalDate getRequestDate(){return requestDate;} public void setRequestDate(LocalDate v){requestDate=v;}
}
