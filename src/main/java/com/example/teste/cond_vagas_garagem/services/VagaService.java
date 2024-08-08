package com.example.teste.cond_vagas_garagem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.dtos.VagaDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.repositories.VagaRepository;

import jakarta.transaction.Transactional;

@Service
public class VagaService {

	@Autowired
	private VagaRepository vagaRepository;
	
	@Autowired
	private MoradorService moradorService;
	
	public VagaService(VagaRepository vagaRepository, MoradorService moradorService) {
		this.vagaRepository = vagaRepository;
		this.moradorService = moradorService;
	}
	
	@Transactional
	public Vaga saveVaga(VagaDto vagaDto) {
		
		Morador objMorador = moradorService.getMoradorById(vagaDto.getMoradorId()).get();
		
		Vaga vaga = new Vaga(
				vagaDto.getNumeroDaVaga(), 
				vagaDto.getEhAlugada(), 
				vagaDto.getMoradorQueAlugou(), 
				objMorador, 
				vagaDto.getVeiculoId());
		
		validateFields(vaga);
		return vagaRepository.save(vaga);
	}

	public Optional<Vaga> getVagaById(Long id) {
		
		Optional<Vaga> vagaOpt = vagaRepository.findById(id);
		
		if(vagaOpt.isEmpty()) {
			throw new NotFoundObjectException("O id: "+ id.toString() +" não se encontra na base de Dados");
		}
		return vagaOpt;
	}
	
	@Transactional
	public Vaga updateVagaById(Long id, VagaDto vagaDto) {
		
		Vaga uptVaga = this.getVagaById(id).get();
		uptVaga.setNumeroDaVaga(vagaDto.getNumeroDaVaga());
		uptVaga.setEhAlugada(vagaDto.getEhAlugada());
		uptVaga.setMoradorQueAlugou(vagaDto.getMoradorQueAlugou());
		uptVaga.setMorador(moradorService.getMoradorById(vagaDto.getMoradorId()).get());
		uptVaga.setVeiculoId(vagaDto.getVeiculoId());
		
		validateFields(uptVaga);
		
		uptVaga.setId(id);
		return vagaRepository.save(uptVaga);
	}
	
	@Transactional
	public void deleteVagaById(Long id) {
		
		Vaga deleteVaga = this.getVagaById(id).get();
		vagaRepository.deleteById(deleteVaga.getId());
	}
	
	private void validateFields(Vaga vaga) {
		
		if(vaga.getNumeroDaVaga() == null || vaga.getNumeroDaVaga().isBlank()) {
			throw new NotNullableFieldsException("O campo numero da vaga não pode ser nulo ou vazio!");
		} else if(vaga.getEhAlugada() == null) {
			throw new NotNullableFieldsException("O campo eh alugada não pode ser nulo!");
		} else if(vaga.getEhAlugada() == true && vaga.getMoradorQueAlugou() == null) {
			throw new NotNullableFieldsException("O campo morador que alugou não pode ser nulo!");
		} else if(vaga.getMorador().getId() == null) {
			throw new NotNullableFieldsException("O campo morador não pode ser nulo!");
		}		
	}
}
