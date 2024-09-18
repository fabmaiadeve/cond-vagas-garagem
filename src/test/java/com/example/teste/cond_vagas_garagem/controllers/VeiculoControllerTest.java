package com.example.teste.cond_vagas_garagem.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.services.VeiculoService;

class VeiculoControllerTest {

	@Mock private VeiculoService service;
	@InjectMocks VeiculoController controller;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCadastrarVeiculoDeveRetornarHttpStatus201() {
		
		VeiculoDto validVeiculoDto = new VeiculoDto("validPlaca", "validModelo", "validCor", 1L, 1L);
		VeiculoDto validVeiculoDtoSaved = new VeiculoDto("validPlaca", "validModelo", "validCor", 1L, 1L);
		
		when(service.saveVeiculo(validVeiculoDto)).thenReturn(validVeiculoDtoSaved);
		
		ResponseEntity<VeiculoDto> response = controller.addVeiculo(validVeiculoDto);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(validVeiculoDtoSaved, response.getBody());
	}
	
	@Test
	public void testCadastrarVeiculoDeveRetornarHttpStatus406() {
		
		VeiculoDto invalidVeiculoDto = null;
		
		doThrow(NotNullableFieldsException.class).when(service).saveVeiculo(invalidVeiculoDto);
		
		assertThrows(NotNullableFieldsException.class, () -> controller.addVeiculo(invalidVeiculoDto));
	}
	
	@Test
	public void testFindVeiculoByIdDeveRetornarHttp200() {
	
		Morador validMorador = new Morador("validNomedoMorador", "validApartamento", "validBloco");
		Vaga validVaga = new Vaga("validNumeroVaga", true, 1L, validMorador);
		Veiculo validVeiculo = new Veiculo("validPlaca", "validModelo", "validCor", validMorador, validVaga);
		Long validId = 1L;
		validVeiculo.setId(validId);
		Optional<Veiculo> expectedVeiculo = Optional.of(validVeiculo);
		VeiculoDto validVeiculoDto = new VeiculoDto(
				expectedVeiculo.get().getPlacaDoVeiculo(),
				expectedVeiculo.get().getModeloDoVeiculo(),
				expectedVeiculo.get().getCorDoVeiculo(),
				expectedVeiculo.get().getMorador().getId(),
				expectedVeiculo.get().getVaga().getId());
		
		when(service.getVeiculoById(validId)).thenReturn(expectedVeiculo.get());
		
		ResponseEntity<VeiculoDto> response = controller.getVeiculoById(validId);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(validVeiculoDto, response.getBody());
	}

	@Test
	public void testFindVeiculoByIdDeveRetornarHttp404() {
	
		Long invalidId = 99L;
		
		doThrow(NotFoundObjectException.class).when(service).getVeiculoById(invalidId);
		
		assertThrows(NotFoundObjectException.class, () -> controller.getVeiculoById(invalidId));
	}
	
	@Test
	public void testUpdateVeiculoByIdDeveRetornarHttp204() {
	
		Morador validMorador = new Morador("validNomedoMorador", "validApartamento", "validBloco");
		Vaga validVaga = new Vaga("validNumeroVaga", true, 1L, validMorador);
		Long validId = 1L;
		VeiculoDto uptVeiculoDto = new VeiculoDto("uptPlaca", "uptModelo", "uptCor", 1L, 1L);
		Veiculo updatedVeiculo = new Veiculo("updatedPlaca", "updatedModelo", "updatedCor", validMorador, validVaga);
		VeiculoDto expectedVeiculo = new VeiculoDto(
				updatedVeiculo.getPlacaDoVeiculo(),
				updatedVeiculo.getModeloDoVeiculo(),
				updatedVeiculo.getCorDoVeiculo(),
				updatedVeiculo.getMorador().getId(),
				updatedVeiculo.getVaga().getId());
				
		when(service.updateVeiculoById(validId, uptVeiculoDto)).thenReturn(updatedVeiculo);
		
		ResponseEntity<VeiculoDto> response = controller.updateVeiculoById(validId, uptVeiculoDto);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(expectedVeiculo, response.getBody());
	}
	
	@Test
	public void testUpdateVeiculoByIdDeveRetornarHttp404() {
	
		Long invalidId = 99L;
		VeiculoDto uptVeiculoDto = new VeiculoDto("uptPlaca", "uptModelo", "uptCor", 1L, 1L);
		
		doThrow(NotFoundObjectException.class).when(service).updateVeiculoById(invalidId, uptVeiculoDto);
		
		assertThrows(NotFoundObjectException.class, () -> controller.updateVeiculoById(invalidId, uptVeiculoDto));
	}
	
	@Test
	public void testDeleteVeiculoByIdDeveRetornarHttp204() {
	
		Long validId = 1L;
		
		doNothing().when(service).deleteVeiculoById(validId);
		
		ResponseEntity<Void> response = controller.deleteVeiculoById(validId);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		verify(service).deleteVeiculoById(validId);
	}
	
	@Test
	public void testDeleteVeiculoByIdDeveRetornarHttp404() {
	
		Long invalidId = 99L;
		
		doThrow(NotFoundObjectException.class).when(service).deleteVeiculoById(invalidId);
		
		assertThrows(NotFoundObjectException.class, () -> controller.deleteVeiculoById(invalidId));		
	}
}
