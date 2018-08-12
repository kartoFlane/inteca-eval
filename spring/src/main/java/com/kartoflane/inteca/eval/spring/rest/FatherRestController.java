package com.kartoflane.inteca.eval.spring.rest;

import com.kartoflane.inteca.eval.spring.data.entity.Family;
import com.kartoflane.inteca.eval.spring.data.entity.Father;
import com.kartoflane.inteca.eval.spring.data.repository.FamilyRepository;
import com.kartoflane.inteca.eval.spring.data.repository.FatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/father")
public class FatherRestController {
	@Autowired
	private FatherRepository fatherRepository;
	@Autowired
	private FamilyRepository familyRepository;


	@GetMapping()
	Collection<Father> readFathers(Father input) {
		if (input == null) {
			return fatherRepository.findAll();
		} else {
			return fatherRepository.findAll(Example.of(input));
		}
	}

	@GetMapping("/{fatherId}")
	Father readFather(@PathVariable Integer fatherId) {
		return fatherRepository.findById(fatherId)
				.orElseThrow(() -> new FatherNotFoundException(fatherId));
	}

	@PostMapping
	ResponseEntity<?> addFatherToFamily(Integer familyId, @RequestBody Father input) {
		if (!input.isValid() || input.getId() != null) {
			return ResponseEntity.badRequest().build();
		}

		Family family = familyRepository.findById(familyId)
				.orElseThrow(() -> new FamilyNotFoundException(familyId));

		input.setFamily(family);
		Father result = fatherRepository.save(input);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(result.getId())
				.toUri();

		return ResponseEntity.created(location).body(result);
	}
}
