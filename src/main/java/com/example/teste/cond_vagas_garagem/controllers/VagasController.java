package com.example.teste.cond_vagas_garagem.controllers;

import org.springframework.http.ResponseEntity;

import com.example.teste.springswaggercodegen3.api.VagasApi;
import com.example.teste.springswaggercodegen3.model.Vaga;
import com.example.teste.springswaggercodegen3.model.VagaDto;

import jakarta.validation.Valid;


public class VagasController implements VagasApi {
	
	
	public VagasController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ResponseEntity<Void> _addVaga(@Valid VagaDto vaga) {
		// TODO Auto-generated method stub
		return VagasApi.super._addVaga(vaga);
	}
	
	@Override
	public ResponseEntity<VagaDto> _getVagaById(Long vagasId) {
		// TODO Auto-generated method stub
		return VagasApi.super._getVagaById(vagasId);
	}
	
	@Override
	public ResponseEntity<Void> _updateVagaById(Long vagasId, @Valid VagaDto vaga) {
		// TODO Auto-generated method stub
		return VagasApi.super._updateVagaById(vagasId, vaga);
	}
	
	@Override
	public ResponseEntity<Void> _deleteVagaById(Long vagasId) {
		// TODO Auto-generated method stub
		return VagasApi.super._deleteVagaById(vagasId);
	}
	
	
}
