package com.pz.offersservice.offers.repository;

import com.pz.offersservice.offers.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OffersRepository extends JpaRepository<Offer, Long> {
}
