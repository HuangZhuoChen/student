package com.situ.student.entity;

public class Banji {
	private Integer id;
	private String name;

	public Banji() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Banji(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Banji [id=" + id + ", name=" + name + "]";
	}

}
