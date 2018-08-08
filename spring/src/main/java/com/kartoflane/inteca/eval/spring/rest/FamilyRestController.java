package com.kartoflane.inteca.eval.spring.rest;

import com.kartoflane.inteca.eval.spring.data.entity.Child;
import com.kartoflane.inteca.eval.spring.data.entity.Family;
import com.kartoflane.inteca.eval.spring.data.repository.ChildRepository;
import com.kartoflane.inteca.eval.spring.data.repository.FamilyRepository;
import com.kartoflane.inteca.eval.spring.data.repository.FatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
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
	private FatherRepository fatherRepository;
	@Autowired
	private ChildRepository childRepository;

	@GetMapping
	Collection<Family> readFamilies() {
		return familyRepository.findAll();
	}

	@PostMapping()
	ResponseEntity<?> createFamily() {
		Family family = familyRepository.save(new Family());

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(family.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{familyId}")
	Family readFamily(@PathVariable Long familyId) {
		return familyRepository.findById(familyId)
				.orElseThrow(() -> new FamilyNotFoundException(familyId));
	}

	@PutMapping("/{familyId}")
	ResponseEntity<?> modifyFamily(@PathVariable Long familyId, @RequestBody ModifyFamilyPayload input) {
		if (input.isValid()) {
			if (input.hasFatherId()) {
				return addFatherToFamily(familyId, input.getFatherId());
			} else if (input.hasChildId()) {
				return addChildToFamily(familyId, input.getChildId());
			} else {
				// This path can only be hit in case of an implementation error in the server.
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Implementation error");
			}
		}

		return ResponseEntity.badRequest().build();
	}

	ResponseEntity<?> addFatherToFamily(Long familyId, Long fatherId) {
		return familyRepository.findById(familyId)
				.map(family -> fatherRepository.findById(fatherId)
						.map(father -> {
							if (father.getFamilyId() == null) {
								father.setFamily(family);
								fatherRepository.save(father);

								return ResponseEntity.noContent().build();
							} else {
								// Already has a father.
								// 'Add' implies that it should only set father if it doesn't exist,
								// so we disallow updating a family that already has a father.
								return ResponseEntity.status(HttpStatus.CONFLICT).build();
							}
						})
						.orElseThrow(() -> new FatherNotFoundException(fatherId))
				)
				.orElseThrow(() -> new FamilyNotFoundException(familyId));
	}

	ResponseEntity<?> addChildToFamily(Long familyId, Long childId) {
		return familyRepository.findById(familyId)
				.map(family -> childRepository.findById(childId)
						.map(child -> {
							if (child.getFamilyId() == null) {
								child.setFamily(family);
								childRepository.save(child);

								return ResponseEntity.noContent().build();
							} else {
								// Already contains the child
								return ResponseEntity.status(HttpStatus.CONFLICT).build();
							}
						})
						.orElseThrow(() -> new ChildNotFoundException(childId))
				)
				.orElseThrow(() -> new FamilyNotFoundException(familyId));
	}

	@GetMapping("/search")
	Collection<Family> searchFamily(Child input) {
		return familyRepository.findAllById(
				childRepository.findAll(Example.of(input)).stream()
						.map(Child::getFamilyId)
						.collect(Collectors.toList())
		);
	}
}
