package com.example.teste.cond_vagas_garagem.services;

import java.util.Optional;

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
	
	public Optional<Morador> getById(Long id) {
		
		Optional<Morador> moradorOpt = moradorRepository.findById(id);
		
		if(moradorOpt.isEmpty()) {
			
		}
		
		return moradorOpt;
	}
	
	@Transactional
	public Morador updateById(Long id, MoradorDto moradorDto) {
		
		Morador uptMorador = this.getById(id).get();
		uptMorador.setNomeDoMorador(moradorDto.getNomeDoMorador());
		uptMorador.setApartamento(moradorDto.getApartamento());
		uptMorador.setBloco(moradorDto.getBloco());
		
		uptMorador.setId(id);		
		return moradorRepository.save(uptMorador);
	}
	
	@Transactional
	public void deleteById(Long id) {
		
		Morador deleteMorador = this.getById(id).get();
		moradorRepository.deleteById(deleteMorador.getId());
	}
}
