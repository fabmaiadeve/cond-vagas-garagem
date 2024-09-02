package com.example.teste.cond_vagas_garagem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.services.VeiculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
	
	@Autowired
	private VeiculoService veiculoService;

	public VeiculoController(VeiculoService veiculoService) {
		this.veiculoService = veiculoService;
	}
	
	@PostMapping
	public ResponseEntity<VeiculoDto> addVeiculo(@RequestBody VeiculoDto veiculoDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.saveVeiculo(veiculoDto));
	}
	
	@GetMapping("/{veiculosId}")
	public ResponseEntity<VeiculoDto> getVeiculoById(@PathVariable(value = "veiculosId") Long veiculosId) {
		
		Veiculo veiculo = veiculoService.getVeiculoById(veiculosId);
		VeiculoDto veiculoResponse = new VeiculoDto(veiculo.getPlacaDoVeiculo(), veiculo.getModeloDoVeiculo(), veiculo.getCorDoVeiculo(), veiculo.getMorador().getId(), veiculo.getVaga().getId());
		return ResponseEntity.status(HttpStatus.OK).body(veiculoResponse);
	}
	
	@PutMapping("/{veiculosId}")
	public ResponseEntity<VeiculoDto> updateVeiculoById(@PathVariable(value = "veiculosId") Long veiculosId, @Valid @RequestBody VeiculoDto veiculoDto){
		
		Veiculo veiculo = veiculoService.updateVeiculoById(veiculosId, veiculoDto);
		VeiculoDto veiculoResponse = new VeiculoDto(veiculo.getPlacaDoVeiculo(), veiculo.getModeloDoVeiculo(), veiculo.getCorDoVeiculo(), veiculo.getMorador().getId(), veiculo.getVaga().getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(veiculoResponse);
	}
	
	@DeleteMapping("/{veiculosId}")
	public ResponseEntity<Void> deleteVeiculoById(@PathVariable(value = "veiculosId") Long veiculosId) {
		
		veiculoService.deleteVeiculoById(veiculosId);
		return ResponseEntity.noContent().build();
	}
}
