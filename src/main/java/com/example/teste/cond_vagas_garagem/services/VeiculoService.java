package com.example.teste.cond_vagas_garagem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.repositories.VeiculoRepository;

import jakarta.transaction.Transactional;

@Service
public class VeiculoService {
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private MoradorService moradorService;
	
	@Autowired
	private VagaService vagaService;

	public VeiculoService(VeiculoRepository veiculoRepository, MoradorService moradorService, VagaService vagaService) {
		this.veiculoRepository = veiculoRepository;
		this.moradorService = moradorService;
		this.vagaService = vagaService;
	}

	@Transactional
	public VeiculoDto saveVeiculo(VeiculoDto veiculoDto) {
		
		Morador objMorador = moradorService.getMoradorById(veiculoDto.getMoradorId());
		Vaga objVaga = vagaService.getVagaById(veiculoDto.getVagaId());
		
		Veiculo veiculo = new Veiculo(
				veiculoDto.getPlacaDoVeiculo(), 
				veiculoDto.getModeloDoVeiculo(), 
				veiculoDto.getCorDoVeiculo(), 
				objMorador, 
				objVaga);
		
		validateFields(veiculo);
		veiculoRepository.save(veiculo);
		
		VeiculoDto veiculoDtoResponse = new VeiculoDto(
				veiculo.getPlacaDoVeiculo(), 
				veiculo.getModeloDoVeiculo(), 
				veiculo.getCorDoVeiculo(), 
				veiculo.getMorador().getId(),
				veiculo.getVaga().getId());
		
		return veiculoDtoResponse;
	}
	
	public Veiculo getVeiculoById(Long id) {
		
		Optional<Veiculo> optVeiculo = veiculoRepository.findById(id);
		
		if(optVeiculo.isEmpty()) {
			throw new NotFoundObjectException("O id: "+ id.toString() +" não se encontra na base de dados!");
		}
		return optVeiculo.get();
	}
	
	@Transactional
	public Veiculo updateVeiculoById(Long id, VeiculoDto veiculoDto) {
		
		Veiculo uptVeiculo = this.getVeiculoById(id);
		uptVeiculo.setPlacaDoVeiculo(veiculoDto.getPlacaDoVeiculo());
		uptVeiculo.setModeloDoVeiculo(veiculoDto.getModeloDoVeiculo());
		uptVeiculo.setCorDoVeiculo(veiculoDto.getCorDoVeiculo());
		
		validateFields(uptVeiculo);
		
		uptVeiculo.setId(id);				
		return veiculoRepository.save(uptVeiculo);
	}
	
	@Transactional
	public void deleteVeiculoById(Long id) {
		
		Veiculo deleteVeiculo = this.getVeiculoById(id);
		veiculoRepository.deleteById(deleteVeiculo.getId());
	}

	private void validateFields(Veiculo veiculo) {
		
		if(veiculo.getPlacaDoVeiculo() == null || veiculo.getPlacaDoVeiculo().isBlank()) {
			throw new NotNullableFieldsException("O campo placa do veículo não pode ser nulo ou vazio!");
		} else if(veiculo.getModeloDoVeiculo() == null || veiculo.getModeloDoVeiculo().isBlank()) {
			throw new NotNullableFieldsException("O campo modelo do veículo não pode ser nulo ou vazio!");
		} else if(veiculo.getCorDoVeiculo() == null || veiculo.getCorDoVeiculo().isBlank()) {
			throw new NotNullableFieldsException("O campo cor do veículo não pode ser nulo ou vazio!");
		} else if(veiculo.getMorador() == null) {
			throw new NotNullableFieldsException("O campo morador não pode ser nulo!");
		} else if(veiculo.getVaga() == null) {
			throw new NotNullableFieldsException("O campo vaga não pode ser nulo!");
		}		
	}
}
