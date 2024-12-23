package com.example.teste.cond_vagas_garagem.dtos;

import java.util.Objects;

import com.example.teste.cond_vagas_garagem.enums.UserRole;

public class UserDto {
	
	private String username;
	
	private String password;
	
	private UserRole role;
	
	public UserDto() {
	}

	public UserDto(String username, String password, UserRole role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserDto [username=" + username + ", password=" + password + ", role=" + role + "]";
	}	
}
