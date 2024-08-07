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

import com.example.teste.cond_vagas_garagem.dtos.MoradorDto;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.services.MoradorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/moradores")
public class MoradorController {
	
	@Autowired
	private MoradorService moradorService;
	
	public MoradorController(MoradorService moradorService) {
		this.moradorService = moradorService;
	}
	
	@PostMapping
	public ResponseEntity<Morador> addMorador(@RequestBody MoradorDto moradorDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(moradorService.saveMorador(moradorDto));
	}
	
	@GetMapping("/{moradorId}")
	public ResponseEntity<Morador> getMoradorById(@PathVariable (value = "moradorId") Long moradorId) {
		
		Optional<Morador> moradorOpt = moradorService.getMoradorById(moradorId);
		return ResponseEntity.status(HttpStatus.OK).body(moradorOpt.get());
	}
	
	@PutMapping("/{moradorId}")
	public ResponseEntity<Morador> updateMoradorById(@PathVariable (value = "moradorId") Long moradorId, @RequestBody @Valid MoradorDto moradorDto) {
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(moradorService.updateMoradorById(moradorId, moradorDto));
	}
	
	@DeleteMapping("/{moradorId}")
	public ResponseEntity<Void> deleteMoradorById(@PathVariable (value = "moradorId") Long moradorId) {
		
		moradorService.deleteMoradorById(moradorId);
		return ResponseEntity.noContent().build();
	}
}
