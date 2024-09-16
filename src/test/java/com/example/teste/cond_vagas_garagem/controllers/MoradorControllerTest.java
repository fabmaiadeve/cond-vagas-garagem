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

import com.example.teste.cond_vagas_garagem.dtos.MoradorDto;
import com.example.teste.cond_vagas_garagem.exceptions.ConstraintViolationException;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.services.MoradorService;

class MoradorControllerTest {
	
	@Mock private MoradorService service;
	@InjectMocks private MoradorController controller;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCadastrarMoradorDeveRetornarHttpStatus201() {
		
		MoradorDto validMoradorDto = new MoradorDto("validNomedoMorador", "validApartamento", "validBloco");
		MoradorDto validMoradorSaved = new MoradorDto("validNomedoMorador", "validApartamento", "validBloco");
		
		when(service.saveMorador(validMoradorDto)).thenReturn(validMoradorSaved);
		
		ResponseEntity<MoradorDto> response = controller.addMorador(validMoradorDto);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(validMoradorSaved, response.getBody());
	}
	
	@Test
	public void testCadastrarMoradorDeveRetornarHttpStatus406() {
		
		MoradorDto invalidMoradorDto = null;
		
		doThrow(NotNullableFieldsException.class).when(service).saveMorador(null);
		
		assertThrows(NotNullableFieldsException.class, () -> controller.addMorador(invalidMoradorDto));
	}
	
	@Test
	public void testFindMoradorByIdDeveRetornarHttp200() {
		
		Long validId = 1L;
		Morador validMorador = new Morador();
		validMorador.setId(validId);
		Optional<Morador> expectedMorador = Optional.of(validMorador);
		MoradorDto validMoradorDto = new MoradorDto(
				expectedMorador.get().getNomeDoMorador(),
				expectedMorador.get().getApartamento(),
				expectedMorador.get().getBloco());
		
		when(service.getMoradorById(validId)).thenReturn(expectedMorador.get());
		
		ResponseEntity<MoradorDto> response = controller.getMoradorById(validId);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());	
		assertEquals(validMoradorDto, response.getBody());		
	}
	
	@Test
	public void testFindMoradorByIdDeveRetornarHttp404() {
		
		Long invalidId = 99L;
		
		doThrow(NotFoundObjectException.class).when(service).getMoradorById(invalidId);
		
		assertThrows(NotFoundObjectException.class, () -> controller.getMoradorById(invalidId));
	}
	
	@Test
	public void testUpdateMoradorByIdDeveRetornarHttp204() {
		
		Long validId = 1L;
		MoradorDto uptMoradorDto = new MoradorDto("uptValidNomedoMorador", "uptValidApartamento", "uptValidBloco");
		Morador updatedMorador = new Morador("updatedNomedoMorador", "updatedApartamento", "updatedBloco");
		MoradorDto uptValidMoradorDto = new MoradorDto(
				updatedMorador.getNomeDoMorador(),
				updatedMorador.getApartamento(),
				updatedMorador.getBloco());
		
		when(service.updateMoradorById(validId, uptMoradorDto)).thenReturn(updatedMorador);
		
		ResponseEntity<MoradorDto> response = controller.updateMoradorById(validId, uptMoradorDto);
		
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertEquals(uptValidMoradorDto, response.getBody());
	}
	
	@Test
	public void testUpdateMoradorByIdDeveRetornarHttp404() {
		
		Long invalidId = 99L;
		MoradorDto uptMoradorDto = new MoradorDto("uptValidNomedoMorador", "uptValidApartamento", "uptValidBloco");
		
		doThrow(NotFoundObjectException.class).when(service).updateMoradorById(invalidId, uptMoradorDto);
		
		assertThrows(NotFoundObjectException.class, () -> controller.updateMoradorById(invalidId, uptMoradorDto));
	}

	@Test
	public void testDeleteMoradorByIdDeveRetornarHttp204() {
		
		Long validId = 1L;
		
		doNothing().when(service).deleteMoradorById(validId);
		
		ResponseEntity<Void> response = controller.deleteMoradorById(validId);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service).deleteMoradorById(validId);
	}
	
	@Test
	public void testDeleteMoradorByIdDeveRetornarHttp404() {
		
		Long invalidId = 99L;
		MoradorDto delMoradorDto = new MoradorDto("uptValidNomedoMorador", "uptValidApartamento", "uptValidBloco");
		
		doThrow(NotFoundObjectException.class).when(service).deleteMoradorById(invalidId);
		
		assertThrows(NotFoundObjectException.class, () -> controller.deleteMoradorById(invalidId));
	}
	
	@Test
	public void testNaoDeletarMoradorPorquePossuiRegistroDeOutrasEntidadesDeveRetornar409() {
		
		Long validId = 1L;
		
		doThrow(ConstraintViolationException.class).when(service).deleteMoradorById(validId);
		
		assertThrows(ConstraintViolationException.class, () -> controller.deleteMoradorById(validId));
	}
}
