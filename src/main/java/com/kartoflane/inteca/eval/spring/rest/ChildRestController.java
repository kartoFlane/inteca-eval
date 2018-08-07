package com.kartoflane.inteca.eval.spring.rest;

import com.kartoflane.inteca.eval.spring.data.entity.Child;
import com.kartoflane.inteca.eval.spring.data.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/child")
public class ChildRestController {
	@Autowired
	private ChildRepository childRepository;

	@GetMapping
	Collection<Child> readChildren() {
		return childRepository.findAll();
	}

	@PostMapping
	ResponseEntity<?> createChild(@RequestBody Child input) {
		Child result = childRepository.save(new Child(
				input.getFirstName(),
				input.getSecondName(),
				input.getSex(),
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

	@GetMapping("/{childId}")
	Child readChild(@PathVariable Long childId) {
		return childRepository.findById(childId)
				.orElseThrow(() -> new ChildNotFoundException(childId));
	}

	@PostMapping("/search")
	Collection<Child> searchChild(@RequestBody Child input) {
		return childRepository.findAll(Example.of(input));
	}
}