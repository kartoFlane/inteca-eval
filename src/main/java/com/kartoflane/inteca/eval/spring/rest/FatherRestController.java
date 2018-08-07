package com.kartoflane.inteca.eval.spring.rest;

import com.kartoflane.inteca.eval.spring.data.entity.Father;
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

	@GetMapping
	Collection<Father> readFathers() {
		return fatherRepository.findAll();
	}

	@PostMapping
	ResponseEntity<?> createFather(@RequestBody Father input) {
		Father result = fatherRepository.save(new Father(
				input.getFirstName(),
				input.getSecondName(),
				input.getPesel(),
				input.getBirthDate()
		));

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(result.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{fatherId}")
	Father readFather(@PathVariable Long fatherId) {
		return fatherRepository.findById(fatherId)
				.orElseThrow(() -> new FatherNotFoundException(fatherId));
	}

	@GetMapping("/search")
	Collection<Father> searchFather(@RequestBody Father input) {
		return fatherRepository.findAll(Example.of(input));
	}
}
