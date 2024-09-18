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

import com.example.teste.cond_vagas_garagem.dtos.VagaDto;
import com.example.teste.cond_vagas_garagem.exceptions.ConstraintViolationException;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.services.VagaService;

class VagaControllerTest {

	@Mock private VagaService service;
	@InjectMocks private VagaController controller;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCadastrarVagaDeveRetornarHttpStatus201() {
		
		VagaDto validVagaDto = new VagaDto("validNumeroVaga", true, 1L, 2L);
		VagaDto validVagaDtoSaved = new VagaDto("validNumeroVaga", true, 1L, 2L);
		
		when(service.saveVaga(validVagaDto)).thenReturn(validVagaDtoSaved);
		
		ResponseEntity<VagaDto> response = controller.addVaga(validVagaDto);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(validVagaDtoSaved, response.getBody());
	}
	
	@Test
	public void testCadastrarVagaDeveRetornarHttpStatus409() {
		
		VagaDto invalidVagaDto = new VagaDto("validNumeroVaga", true, 1L, 2L);
		
		doThrow(ConstraintViolationException.class).when(service).saveVaga(invalidVagaDto);
		
		assertThrows(ConstraintViolationException.class, () -> controller.addVaga(invalidVagaDto));
	}
	
	@Test
	public void testCadastrarVagaDeveRetornarHttpStatus406() {
		
		VagaDto invalidVagaDto = null;
		
		doThrow(NotNullableFieldsException.class).when(service).saveVaga(invalidVagaDto);
		
		assertThrows(NotNullableFieldsException.class, () -> controller.addVaga(invalidVagaDto));
	}
	
	@Test
	public void testFindVagaByIdDeveRetornarHttp200() {
		
		Morador validMorador = new Morador("validNomedoMorador", "validApartamento", "validBloco");
		Long validId = 1L;
		Vaga validVaga = new Vaga("validNumeroVaga", true, 1L, validMorador);
		validVaga.setId(validId);
		Optional<Vaga> expectedVaga = Optional.of(validVaga);
		VagaDto validVagaDto = new VagaDto(
				expectedVaga.get().getNumeroDaVaga(),
				expectedVaga.get().getEhAlugada(),
				expectedVaga.get().getMoradorQueAlugou(),
				expectedVaga.get().getMorador().getId());
		
		when(service.getVagaById(validId)).thenReturn(expectedVaga.get());
		
		ResponseEntity<VagaDto> response = controller.getVagaById(validId);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(validVagaDto, response.getBody());	
	}
	
	@Test
	public void testFindVagaByIdDeveRetornarHttp404() {
		
		Long invalidId = 99L;
		
		doThrow(NotFoundObjectException.class).when(service).getVagaById(invalidId);
		
		assertThrows(NotFoundObjectException.class, () -> controller.getVagaById(invalidId));
	}
	
	@Test
	public void testUpdateVagaByIdDeveRetornarHttp204() {
		
		Morador validMorador = new Morador("validNomedoMorador", "validApartamento", "validBloco");
		validMorador.setId(2L);
		Long validId = 1L;
		VagaDto uptVagaDto = new VagaDto("uptValidNumeroVaga", true, 1L, 2L);
		Vaga updatedVaga = new Vaga("updatedNumeroVaga", true, 1L, validMorador);
		VagaDto expectedVagaDto = new VagaDto(
				updatedVaga.getNumeroDaVaga(),
				updatedVaga.getEhAlugada(),
				updatedVaga.getMoradorQueAlugou(),
				updatedVaga.getMorador().getId());
		
		when(service.updateVagaById(validId, uptVagaDto)).thenReturn(updatedVaga);
		
		ResponseEntity<VagaDto> response = controller.updateVagaById(validId, uptVagaDto);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(expectedVagaDto, response.getBody());		
	}
	
	@Test
	public void testUpdateVagaByIdDeveRetornarHttp404() {
		
		Long invalidId = 99L;
		VagaDto uptVagaDto = new VagaDto("uptValidNumeroVaga", true, 1L, 2L);
		
		doThrow(NotFoundObjectException.class).when(service).updateVagaById(invalidId, uptVagaDto);
		
		assertThrows(NotFoundObjectException.class, () -> controller.updateVagaById(invalidId, uptVagaDto));
	}
	
	@Test
	public void testDeleteVagaByIdDeveRetornarHttp204() {
		
		Long validId = 1L;
		
		doNothing().when(service).deleteVagaById(validId);
		
		ResponseEntity<Void> response = controller.deleteVagaById(validId);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service).deleteVagaById(validId);
	}
	
	@Test
	public void testDeleteVagaByIdDeveRetornarHttp404() {
		
		Long invalidId = 99L;
		
		doThrow(NotFoundObjectException.class).when(service).deleteVagaById(invalidId);
		
		assertThrows(NotFoundObjectException.class, () -> controller.deleteVagaById(invalidId));
	}
}
