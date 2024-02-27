package com.group.libraryapp.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 20, name = "name") // name varchar(20)
	private String name;
	private Integer age;

	public User() {
	}

	public User(String name, Integer age) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
		}
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public void updateName(String name) {
		this.name = name;
	}
}
