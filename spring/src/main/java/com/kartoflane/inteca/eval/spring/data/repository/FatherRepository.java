package com.kartoflane.inteca.eval.spring.data.repository;

import com.kartoflane.inteca.eval.spring.data.entity.Father;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;

public interface FatherRepository extends JpaRepository<Father, Integer> {
	Collection<Father> findByFirstName(String firstName);

	Collection<Father> findBySecondName(String secondName);

	Collection<Father> findByPesel(String pesel);

	Collection<Father> findByBirthDate(Date birthDate);
}
