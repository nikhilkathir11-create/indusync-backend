package com.medicine.donation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DemoData implements CommandLineRunner {
    private final MedicineRepository medicines;
    private final RequestRepository requests;
    public DemoData(MedicineRepository medicines, RequestRepository requests){this.medicines=medicines;this.requests=requests;}
    @Override public void run(String... args) {
        if (medicines.count()>0) return;
        medicines.save(new Medicine("Paracetamol 500 mg","Tablet",120,LocalDate.now().plusDays(20),"Arun Kumar","DEMO-001"));
        medicines.save(new Medicine("Cetirizine 10 mg","Tablet",60,LocalDate.now().plusDays(120),"Meena S","DEMO-002"));
        medicines.save(new Medicine("ORS Sachet","Sachet",45,LocalDate.now().plusDays(10),"Karthik R","DEMO-003"));
        medicines.save(new Medicine("Vitamin C 500 mg","Tablet",80,LocalDate.now().plusDays(180),"Divya M","DEMO-004"));
        medicines.save(new Medicine("Antiseptic Solution","Bottle",18,LocalDate.now().minusDays(5),"Suresh B","DEMO-005"));
        medicines.save(new Medicine("Bandage Roll","Piece",35,LocalDate.now().plusDays(300),"Priya N","DEMO-006"));
        requests.save(new DonationRequest("Ravi","ORS Sachet",5));
        requests.save(new DonationRequest("Lakshmi","Vitamin C 500 mg",10));
    }
}
