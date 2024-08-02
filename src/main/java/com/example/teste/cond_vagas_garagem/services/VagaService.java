package com.example.teste.cond_vagas_garagem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.repositories.VagaRepository;

@Service
public class VagaService {

	@Autowired
	private VagaRepository vagaRepository;
	
	public VagaService(VagaRepository vagaRepository) {
		this.vagaRepository = vagaRepository;
	}
	
	
}
