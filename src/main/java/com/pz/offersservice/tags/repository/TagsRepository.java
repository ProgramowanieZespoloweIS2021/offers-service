package com.pz.offersservice.tags.repository;

import com.pz.offersservice.tags.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tag, Long> {
}
