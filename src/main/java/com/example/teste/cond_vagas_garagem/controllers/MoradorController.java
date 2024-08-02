package com.example.teste.cond_vagas_garagem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teste.springswaggercodegen3.api.MoradoresApi;
import com.example.teste.springswaggercodegen3.model.MoradorDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/moradores")
public class MoradorController implements MoradoresApi {
	
	public MoradorController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@PostMapping
	public ResponseEntity<Void> _addMorador(@RequestBody @Valid MoradorDto moradorDto) {
		// TODO Auto-generated method stub
		return MoradoresApi.super._addMorador(moradorDto);
	}
	
	@Override
	@GetMapping("/{moradorId}")
	public ResponseEntity<MoradorDto> _getMoradorById(@PathVariable (value = "moradorId") Long moradorId) {
		// TODO Auto-generated method stub
		return MoradoresApi.super._getMoradorById(moradorId);
	}
	
	@Override
	@PutMapping("/{moradorId}")
	public ResponseEntity<Void> _updateMoradorById(@PathVariable (value = "moradorId") Long moradorId, @RequestBody @Valid MoradorDto moradorDto) {
		// TODO Auto-generated method stub
		return MoradoresApi.super._updateMoradorById(moradorId, moradorDto);
	}
	
	@Override
	@DeleteMapping("/{moradorId}")
	public ResponseEntity<Void> _deleteMoradorById(@PathVariable (value = "moradorId") Long moradorId) {
		// TODO Auto-generated method stub
		return MoradoresApi.super._deleteMoradorById(moradorId);
	}

}
