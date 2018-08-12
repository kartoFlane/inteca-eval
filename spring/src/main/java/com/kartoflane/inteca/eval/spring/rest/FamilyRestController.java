package com.kartoflane.inteca.eval.spring.rest;

import com.kartoflane.inteca.eval.spring.data.entity.Child;
import com.kartoflane.inteca.eval.spring.data.entity.Family;
import com.kartoflane.inteca.eval.spring.data.repository.ChildRepository;
import com.kartoflane.inteca.eval.spring.data.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/family")
public class FamilyRestController {
	@Autowired
	private FamilyRepository familyRepository;
	@Autowired
	private ChildRepository childRepository;


	@GetMapping()
	Collection<Family> readFamilies(Child input) {
		if (input == null) {
			return familyRepository.findAll();
		} else {
			return familyRepository.findAllById(
					childRepository.findAll(Example.of(input)).stream()
							.map(Child::getFamilyId)
							.collect(Collectors.toList())
			);
		}
	}

	@GetMapping("/{familyId}")
	Family readFamily(@PathVariable Integer familyId) {
		return familyRepository.findById(familyId)
				.orElseThrow(() -> new FamilyNotFoundException(familyId));
	}

	@PostMapping()
	ResponseEntity<?> createFamily() {
		Family family = familyRepository.save(new Family());

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(family.getId())
				.toUri();

		return ResponseEntity.created(location).body(family);
	}
}
