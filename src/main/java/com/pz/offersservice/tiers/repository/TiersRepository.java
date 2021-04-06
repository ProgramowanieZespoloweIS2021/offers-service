package com.pz.offersservice.tiers.repository;

import com.pz.offersservice.tiers.model.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiersRepository extends JpaRepository<Tier, Long> {
}
