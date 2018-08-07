package com.kartoflane.inteca.eval.spring.data.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Child {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="family_id")
	private Family family;

	private String firstName;
	private String secondName;
	private String pesel;
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	private String sex;


	private Child() {
	}

	public Child(final String firstName, final String secondName, final String sex, final String pesel, final Date birthDate) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.sex = sex;
		this.pesel = pesel;
		this.birthDate = birthDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFamilyId() {
		return family != null
				? family.getId()
				: null;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return String.format(
				"Child[id=%d, firstName='%s', secondName='%s', pesel='%s', birthDate='%s', sex='%s']",
				id, firstName, secondName, pesel, birthDate, sex
		);
	}
}
