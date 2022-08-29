package com.ssafy.backend.model;

public class UserDto {
    private String id;
    private String pass;
    private String name;

    public UserDto() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", pass=" + pass + ", name=" + name + "]";
	}
    
}
