package com.kartoflane.inteca.eval.spring.data.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Family {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "family")
	private Father father;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "family")
	private Set<Child> children = new HashSet<>();


	public Family() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// TBachminski:
	// Entity getter functions return ids instead of entity instances to prevent
	// recursion when constructing JSON representations.
	// (Could use @JsonIgnore, but we actually want this data to be available)
	public Long getFatherId() {
		return father != null
				? father.getId()
				: null;
	}

	public void setFather(Father father) {
		this.father = father;
	}

	public boolean addChild(Child child) {
		return this.children.add(child);
	}

	public Collection<Long> getChildrenIds() {
		return children.stream()
				.map(Child::getId)
				.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableCollection));
	}

	@Override
	public String toString() {
		return String.format(
				"Family[id=%d, children=%s,%n\tfather=%s%n]",
				id, children.size(), father.toString()
		);
	}
}
