package com.kartoflane.inteca.eval.spring.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "family")
	private Father father;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "family")
	private Set<Child> children = new HashSet<>();


	public Family() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Father getFather() {
		return this.father;
	}

	public void setFather(Father father) {
		this.father = father;
	}

	public boolean addChild(Child child) {
		return children.add(child);
	}

	@JsonIgnore
	public Collection<Integer> getChildrenIds() {
		return children.stream()
				.map(Child::getId)
				.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
	}

	public Collection<Child> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	@Override
	public String toString() {
		// Use JSON representations for debugging convenience.
		// Don't care about exceptions at the moment.
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}
}
