package com.example.teste.cond_vagas_garagem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.dtos.VagaDto;
import com.example.teste.cond_vagas_garagem.exceptions.ConstraintViolationException;
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
	public VagaDto saveVaga(VagaDto vagaDto) {
		
		verificaMoradorTemVaga(vagaDto.getMoradorId());
		
		Morador objMorador = moradorService.getMoradorById(vagaDto.getMoradorId());
		
		Vaga vaga = new Vaga(
				vagaDto.getNumeroDaVaga(), 
				vagaDto.getEhAlugada(), 
				vagaDto.getMoradorQueAlugou(), 
				objMorador, 
				vagaDto.getVeiculoId());
		
		validateFields(vaga);
		vagaRepository.save(vaga);
		
		VagaDto vagaDtoResponse = new VagaDto(
				vaga.getNumeroDaVaga(), 
				vaga.getEhAlugada(), 
				vaga.getMoradorQueAlugou(), 
				vaga.getMorador().getId(), 
				vaga.getVeiculoId());
		return vagaDtoResponse;		 
	}

	public Vaga getVagaById(Long id) {
		
		Optional<Vaga> vagaOpt = vagaRepository.findById(id);
		
		if(vagaOpt.isEmpty()) {
			throw new NotFoundObjectException("O id: "+ id.toString() +" não se encontra na base de Dados");
		}
		return vagaOpt.get();
	}
	
	@Transactional
	public Vaga updateVagaById(Long id, VagaDto vagaDto) {
		
		Vaga uptVaga = this.getVagaById(id);
		uptVaga.setNumeroDaVaga(vagaDto.getNumeroDaVaga());
		uptVaga.setEhAlugada(vagaDto.getEhAlugada());
		uptVaga.setMoradorQueAlugou(vagaDto.getMoradorQueAlugou());
		uptVaga.setMorador(moradorService.getMoradorById(vagaDto.getMoradorId()));
		uptVaga.setVeiculoId(vagaDto.getVeiculoId());
		
		validateFields(uptVaga);
		
		uptVaga.setId(id);
		return vagaRepository.save(uptVaga);
	}
	
	@Transactional
	public void deleteVagaById(Long id) {
		
		Vaga deleteVaga = this.getVagaById(id);
		vagaRepository.deleteById(deleteVaga.getId());
	}
	
	private void verificaMoradorTemVaga(Long moradorId) {
		
		Optional<Vaga> optVagaByMoradorId = vagaRepository.findByMoradorId(moradorId);
		
		if(!optVagaByMoradorId.isEmpty()) {
			throw new ConstraintViolationException("Não é possivel fazer a inserção porque o moradorId :" + moradorId + " já se encontra associada a uma vaga cadastrada!");
		}		
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
