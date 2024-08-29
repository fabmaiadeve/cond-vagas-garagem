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

import com.example.teste.cond_vagas_garagem.dtos.VagaDto;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.services.VagaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/vagas")
public class VagaController {
	
	@Autowired
	private VagaService vagaService;
	
	public VagaController(VagaService vagaService) {
		this.vagaService = vagaService;
	}
	
	@PostMapping
	public ResponseEntity<VagaDto> addVaga(@RequestBody VagaDto vagaDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vagaService.saveVaga(vagaDto));
	}
	
	@GetMapping("/{vagasId}")
	public ResponseEntity<VagaDto> getVagaById(@PathVariable(value = "vagasId") Long vagasId) {
		
		Vaga vaga = vagaService.getVagaById(vagasId);
		VagaDto vagaResponse = new VagaDto(vaga.getNumeroDaVaga(), vaga.getEhAlugada(), vaga.getMoradorQueAlugou(), vaga.getMorador().getId(), vaga.getVeiculo().getId());
		return ResponseEntity.status(HttpStatus.OK).body(vagaResponse);
	}
	
	@PutMapping("/{vagasId}")
	public ResponseEntity<VagaDto> updateVagaById(@PathVariable(value = "vagasId") Long vagasId, @Valid @RequestBody VagaDto vagaDto) {
		
		Vaga vaga = vagaService.updateVagaById(vagasId, vagaDto);
		VagaDto vagaResponse = new VagaDto(vaga.getNumeroDaVaga(), vaga.getEhAlugada(), vaga.getMoradorQueAlugou(), vaga.getMorador().getId(), vaga.getVeiculo().getId());		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vagaResponse);
	}
	
	@DeleteMapping("/{vagasId}")
	public ResponseEntity<Void> deleteVagaById(@PathVariable(value = "vagasId") Long vagasId) {
		
		vagaService.deleteVagaById(vagasId);
		return ResponseEntity.noContent().build(); 
	}	
}
