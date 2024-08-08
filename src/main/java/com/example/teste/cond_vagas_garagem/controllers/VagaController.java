package com.example.teste.cond_vagas_garagem.controllers;

import java.util.Optional;

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
	public ResponseEntity<Vaga> addVaga(@RequestBody VagaDto vagaDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vagaService.saveVaga(vagaDto));
	}
	
	@GetMapping("/{vagasId}")
	public ResponseEntity<Vaga> getVagaById(@PathVariable(value = "vagasId") Long vagasId) {
		
		Optional<Vaga> vagaOpt = vagaService.getVagaById(vagasId);
		return ResponseEntity.status(HttpStatus.OK).body(vagaOpt.get());
	}
	
	@PutMapping("/{vagasId}")
	public ResponseEntity<Vaga> updateVagaById(@PathVariable(value = "vagasId") Long vagasId, @Valid @RequestBody VagaDto vagaDto) {
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vagaService.updateVagaById(vagasId, vagaDto));
	}
	
	@DeleteMapping("/{vagasId}")
	public ResponseEntity<Void> deleteVagaById(@PathVariable(value = "vagasId") Long vagasId) {
		
		vagaService.deleteVagaById(vagasId);
		return ResponseEntity.noContent().build(); 
	}	
}
