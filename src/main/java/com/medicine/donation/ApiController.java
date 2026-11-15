package com.medicine.donation;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
    private final MedicineRepository medicines;
    private final RequestRepository requests;

    public ApiController(MedicineRepository medicines, RequestRepository requests) {
        this.medicines=medicines; this.requests=requests;
    }

    @GetMapping("/medicines")
    public List<Medicine> allMedicines(@RequestParam(required=false) String search) {
        return (search == null || search.isBlank()) ? medicines.findAll() : medicines.findByNameContainingIgnoreCase(search);
    }

    @PostMapping("/medicines")
    public ResponseEntity<?> addMedicine(@RequestBody Medicine medicine) {
        if (medicine.getName()==null || medicine.getName().isBlank() || medicine.getQuantity()<0 || medicine.getExpiryDate()==null)
            return ResponseEntity.badRequest().body(Map.of("error","Name, non-negative quantity and expiry date are required."));
        return ResponseEntity.ok(medicines.save(medicine));
    }

    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable Long id) {
        if (!medicines.existsById(id)) return ResponseEntity.notFound().build();
        medicines.deleteById(id); return ResponseEntity.noContent().build();
    }

    @GetMapping("/requests")
    public List<DonationRequest> allRequests(){ return requests.findAll(); }

    @PostMapping("/requests")
    public ResponseEntity<?> createRequest(@RequestBody DonationRequest request) {
        if (request.getRecipientName()==null || request.getRecipientName().isBlank() ||
            request.getMedicineName()==null || request.getMedicineName().isBlank() || request.getQuantity()<=0)
            return ResponseEntity.badRequest().body(Map.of("error","Recipient, medicine and positive quantity are required."));
        request.setStatus("PENDING");
        return ResponseEntity.ok(requests.save(request));
    }

    @PutMapping("/requests/{id}/{decision}")
    public ResponseEntity<?> decideRequest(@PathVariable Long id, @PathVariable String decision) {
        Optional<DonationRequest> found=requests.findById(id);
        if (found.isEmpty()) return ResponseEntity.notFound().build();
        DonationRequest req=found.get();
        if (!"PENDING".equals(req.getStatus())) return ResponseEntity.badRequest().body(Map.of("error","Request already processed."));
        if ("reject".equalsIgnoreCase(decision)) {
            req.setStatus("REJECTED"); return ResponseEntity.ok(requests.save(req));
        }
        if (!"approve".equalsIgnoreCase(decision)) return ResponseEntity.badRequest().body(Map.of("error","Decision must be approve or reject."));
        Optional<Medicine> stock=medicines.findAll().stream()
            .filter(m -> m.getName().equalsIgnoreCase(req.getMedicineName()))
            .filter(m -> m.getQuantity()>=req.getQuantity())
            .filter(m -> !"EXPIRED".equals(m.getExpiryStatus())).findFirst();
        if (stock.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error","No matching non-expired stock with sufficient quantity."));
        Medicine m=stock.get(); m.setQuantity(m.getQuantity()-req.getQuantity()); medicines.save(m);
        req.setStatus("APPROVED"); return ResponseEntity.ok(requests.save(req));
    }

    @GetMapping("/dashboard")
    public Map<String,Object> dashboard() {
        List<Medicine> list=medicines.findAll();
        long expired=list.stream().filter(m -> "EXPIRED".equals(m.getExpiryStatus())).count();
        long soon=list.stream().filter(m -> "EXPIRING_SOON".equals(m.getExpiryStatus())).count();
        long pending=requests.findAll().stream().filter(r -> "PENDING".equals(r.getStatus())).count();
        return Map.of("medicineRecords",list.size(),"totalUnits",list.stream().mapToInt(Medicine::getQuantity).sum(),
            "expiredRecords",expired,"expiringSoonRecords",soon,"pendingRequests",pending);
    }
}
