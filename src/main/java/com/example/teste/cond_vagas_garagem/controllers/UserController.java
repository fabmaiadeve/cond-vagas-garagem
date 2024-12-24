package com.example.teste.cond_vagas_garagem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teste.cond_vagas_garagem.dtos.LoginResponseDto;
import com.example.teste.cond_vagas_garagem.dtos.UserDto;
import com.example.teste.cond_vagas_garagem.models.User;
import com.example.teste.cond_vagas_garagem.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users") 
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> realizarLogin(@RequestBody UserDto userDto) {
		
		String tokenResponse = userService.fazerLogin(userDto);
		return ResponseEntity.ok(tokenResponse);
	} 
	
	@PostMapping
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(userDto));
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable (value = "userId") Long userId) {
		
		User user = userService.getUserById(userId);
		UserDto userResponse = new UserDto(user.getUsername(), user.getPassword(), user.getRole());
		return ResponseEntity.status(HttpStatus.OK).body(userResponse);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateById(@PathVariable (value = "userId") Long userId, @RequestBody @Valid UserDto userDto) {
		
		User user = userService.updateUserById(userId, userDto);
		UserDto userResponse = new UserDto(user.getUsername(), user.getPassword(), user.getRole());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userResponse);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteById(@PathVariable (value = "userId") Long userId) {
		
		userService.deleteById(userId);
		return ResponseEntity.noContent().build();
	}

}
