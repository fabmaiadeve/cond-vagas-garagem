package com.example.teste.cond_vagas_garagem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.dtos.MoradorDto;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.repositories.MoradorRepository;

import jakarta.transaction.Transactional;



@Service
public class MoradorService {

	@Autowired
	private MoradorRepository moradorRepository;
	
	public MoradorService(MoradorRepository moradorRepository) {
		this.moradorRepository = moradorRepository;
	}
	
	@Transactional
	public Morador saveMorador(MoradorDto moradorDto) {
		
		Morador morador = new Morador(
				moradorDto.getNomeDoMorador(), 
				moradorDto.getApartamento(), 
				moradorDto.getBloco());
		
		return moradorRepository.save(morador);
	}
}
