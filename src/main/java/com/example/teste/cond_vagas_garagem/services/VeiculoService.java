package com.example.teste.cond_vagas_garagem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.repositories.VeiculoRepository;

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

	private void validateFields(Veiculo veiculo) {
		// TODO Auto-generated method stub
		
	}
	
	

}
