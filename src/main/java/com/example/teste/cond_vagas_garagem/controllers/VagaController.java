package com.example.teste.cond_vagas_garagem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teste.springswaggercodegen3.api.VagasApi;
import com.example.teste.springswaggercodegen3.model.VagaDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/vagas")
public class VagaController implements VagasApi {
	
	
	public VagaController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@PostMapping
	public ResponseEntity<Void> _addVaga(@RequestBody @Valid VagaDto vaga) {
		// TODO Auto-generated method stub
		return VagasApi.super._addVaga(vaga);
	}
	
	@Override
	@GetMapping("/{vagasId}")
	public ResponseEntity<VagaDto> _getVagaById(@PathVariable(value = "vagasId") Long vagasId) {
		// TODO Auto-generated method stub
		return VagasApi.super._getVagaById(vagasId);
	}
	
	@Override
	@PutMapping("/{vagasId}")
	public ResponseEntity<Void> _updateVagaById(@PathVariable(value = "vagasId") Long vagasId, @Valid @RequestBody VagaDto vaga) {
		// TODO Auto-generated method stub
		return VagasApi.super._updateVagaById(vagasId, vaga);
	}
	
	@Override
	@DeleteMapping("/{vagasId}")
	public ResponseEntity<Void> _deleteVagaById(@PathVariable(value = "vagasId") Long vagasId) {
		// TODO Auto-generated method stub
		return VagasApi.super._deleteVagaById(vagasId);
	}
	
	
}
