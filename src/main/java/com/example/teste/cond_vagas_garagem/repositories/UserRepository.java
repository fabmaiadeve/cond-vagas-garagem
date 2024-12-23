package com.example.teste.cond_vagas_garagem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.teste.cond_vagas_garagem.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByUsername(String username);

}
