package com.example.teste.cond_vagas_garagem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.teste.cond_vagas_garagem.models.Morador;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

}
