package com.example.teste.cond_vagas_garagem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.dtos.MoradorDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
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
		
		validateFields(morador);
		return moradorRepository.save(morador);
	}
	
	public Optional<Morador> getById(Long id) {
		
		Optional<Morador> moradorOpt = moradorRepository.findById(id);
		
		if(moradorOpt.isEmpty()) {
			throw new NotFoundObjectException("O id: "+ id.toString() +" não se encontra na base de dados!");  
		}
		
		return moradorOpt;
	}
	
	@Transactional
	public Morador updateById(Long id, MoradorDto moradorDto) {
		
		Morador uptMorador = this.getById(id).get();
		uptMorador.setNomeDoMorador(moradorDto.getNomeDoMorador());
		uptMorador.setApartamento(moradorDto.getApartamento());
		uptMorador.setBloco(moradorDto.getBloco());
		
		validateFields(uptMorador);
		
		uptMorador.setId(id);		
		return moradorRepository.save(uptMorador);
	}
	
	@Transactional
	public void deleteById(Long id) {
		
		Morador deleteMorador = this.getById(id).get();
		moradorRepository.deleteById(deleteMorador.getId());
	}
	
	private void validateFields(Morador morador) {
		
		if(morador.getNomeDoMorador() == null || morador.getNomeDoMorador().isBlank()) {
			throw new NotNullableFieldsException("O campo do nome do morador não pode ser nulo ou vazio!");
		} else if(morador.getApartamento() == null || morador.getApartamento().isBlank()) {
			throw new NotNullableFieldsException("O campo de apartamento não pode ser nulo ou vazio!");
		} else if(morador.getBloco() == null || morador.getBloco().isBlank()) {
			throw new NotNullableFieldsException("O campo de bloco não pode ser nulo ou vazio!");
		}		
	}
}
