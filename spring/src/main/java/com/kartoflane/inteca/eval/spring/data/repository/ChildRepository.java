package com.kartoflane.inteca.eval.spring.data.repository;

import com.kartoflane.inteca.eval.spring.data.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;

public interface ChildRepository extends JpaRepository<Child, Integer> {
	Collection<Child> findByFirstName(String firstName);

	Collection<Child> findBySecondName(String secondName);

	Collection<Child> findByPesel(String pesel);

	Collection<Child> findByBirthDate(Date birthDate);

	Collection<Child> findBySex(String sex);
}
