package com.medicine.donation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RequestRepository extends JpaRepository<DonationRequest, Long> {}
