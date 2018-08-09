package com.kartoflane.inteca.eval.spring.data.repository;

import com.kartoflane.inteca.eval.spring.data.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Integer> {
}
