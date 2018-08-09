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
		if (
			input.getFirstName() == null ||
			input.getSecondName() == null ||
			input.getPesel() == null ||
			input.getBirthDate() == null
		) {
			return ResponseEntity.badRequest().body("Missing a required field, got: " + input.toString());
		}

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

		return ResponseEntity.created(location).body(result);
	}

	@GetMapping("/{fatherId}")
	Father readFather(@PathVariable Integer fatherId) {
		return fatherRepository.findById(fatherId)
				.orElseThrow(() -> new FatherNotFoundException(fatherId));
	}

	@GetMapping("/search")
	Collection<Father> searchFather(Father input) {
		return fatherRepository.findAll(Example.of(input));
	}
}
