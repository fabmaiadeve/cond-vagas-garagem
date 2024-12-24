package com.example.teste.cond_vagas_garagem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.dtos.UserDto;
import com.example.teste.cond_vagas_garagem.exceptions.ExistentUserLoginException;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.User;
import com.example.teste.cond_vagas_garagem.repositories.UserRepository;
import com.example.teste.cond_vagas_garagem.security.TokenService;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	//private AuthenticationManager authenticationManager;
	
	@Autowired
	TokenService tokenService;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
	public UserDto saveUser(UserDto userDto) {
		
		if(userRepository.findByUsername(userDto.getUsername()) != null) {
			throw new ExistentUserLoginException("O usuário a ser cadastrado já se encontra na base de dados!");
		}
		
		String encriptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
		
		User user = new User(userDto.getUsername(), encriptedPassword, userDto.getRole());
		
		validateFields(user);
		
		userRepository.save(user);
		
		UserDto userResponse = new UserDto(user.getUsername(), user.getPassword(), user.getRole());
		
		return userResponse;
	}	

	public User getUserById(Long userId) {
		
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(userOpt.isEmpty()) {
			throw new NotFoundObjectException("O id: "+ userId.toString() +" não se encontra na base de dados!");
		}
		
		return userOpt.get();
	}

	@Transactional
	public User updateUserById(Long userId, UserDto userDto) {
		
		User uptUser = this.getUserById(userId);
		uptUser.setUsername(userDto.getUsername());
		uptUser.setPassword(userDto.getPassword());
		
		validateFields(uptUser);
		
		uptUser.setId(userId);
		
		return userRepository.save(uptUser);
	}

	@Transactional
	public void deleteById(Long userId) {
		
		User uptUser = this.getUserById(userId);
		userRepository.deleteById(uptUser.getId());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByUsername(username);
	}

	public String fazerLogin(UserDto userDto) {
		
		//var usernamePassword = new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
		//var auth = this.authenticationManager.authenticate(usernamePassword);
		try {
	        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
	        var usernamePassword = new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
	        var auth = authenticationManager.authenticate(usernamePassword);	        
	        var token = tokenService.generateToken((User) auth.getPrincipal());
	        return token;
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Erro durante o login", e);
	    }
	}
	
	private void validateFields(User user) {

		if(user.getUsername() == null || user.getUsername().isBlank()) {
			throw new NotNullableFieldsException("O campo de userName não pode ser nulo ou vazio!");
		} else if(user.getPassword() == null || user.getPassword().isBlank()) {
			throw new NotNullableFieldsException("O campo de password não pode ser nulo ou vazio!");
		} else if(user.getRole() == null) {
			throw new NotNullableFieldsException("O campo de role não pode ser nulo");
		}
	}
}
