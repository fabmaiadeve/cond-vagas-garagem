package com.example.teste.cond_vagas_garagem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.teste.cond_vagas_garagem.dtos.MoradorDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.repositories.MoradorRepository;

@ExtendWith(MockitoExtension.class)
class MoradorServiceTest {
	
	@Mock private MoradorRepository rep;
	@InjectMocks private MoradorService service;
	
	private MoradorDto validMoradorDto;
	private Morador validMorador;

	@BeforeEach
	void setUp() {
		validMoradorDto = new MoradorDto("MoradorSuccess", "apSuccess", "blocoSucess");
		validMorador = new Morador("MoradorSuccess", "apSuccess", "blocoSucess");
	}
	
	@Test
	public void deveSalvarMoradorComSucesso() {
		
		Morador expectedMorador = new Morador(
				validMoradorDto.getNomeDoMorador(),
				validMoradorDto.getApartamento(),
				validMoradorDto.getBloco());
		
		MoradorDto expectedMoradorDto = new MoradorDto(
				expectedMorador.getNomeDoMorador(),
				expectedMorador.getApartamento(),
				expectedMorador.getBloco());
		
		when(rep.save(any())).thenReturn(expectedMorador);
		
		MoradorDto savedMoradorDto = service.saveMorador(validMoradorDto);
		
		assertNotNull(savedMoradorDto);
		assertEquals(expectedMoradorDto, savedMoradorDto);
	}
	
	@Test 
	void testDeveLancarExceptionQuandoNomeDoMoradorEstaNulo() { 
		 
		validMoradorDto.setNomeDoMorador(null);
		
		NotNullableFieldsException exception = assertThrows(NotNullableFieldsException.class, () -> service.saveMorador(validMoradorDto));
		assertEquals("O campo do nome do morador não pode ser nulo ou vazio!", exception.getMessage());
	}
	
	@Test 
	void testDeveLancarExceptionQuandoApartamentoEstaNulo() { 
		
		validMoradorDto.setApartamento(null);
		
		NotNullableFieldsException exception = assertThrows(NotNullableFieldsException.class, () -> service.saveMorador(validMoradorDto));
		assertEquals("O campo de apartamento não pode ser nulo ou vazio!", exception.getMessage());
	}
	
	@Test 
	void testDeveLancarExceptionQuandoBlocoEstaNulo() { 
		
		validMoradorDto.setBloco(null);
		
		NotNullableFieldsException exception = assertThrows(NotNullableFieldsException.class, () -> service.saveMorador(validMoradorDto));
		assertEquals("O campo de bloco não pode ser nulo ou vazio!", exception.getMessage());
	}
	
	@Test
	void testDeveRetornarMoradorPorIdComSucesso() {
		
		validMorador.setId(1L);
		Optional<Morador> expectedMoradorOpt = Optional.of(validMorador);
		
		when(rep.findById(validMorador.getId())).thenReturn(expectedMoradorOpt);
		
		Morador savedMorador = service.getMoradorById(validMorador.getId());
		
		assertEquals(expectedMoradorOpt.get(), savedMorador);
	}
	
	@Test
	void testDeveRetornarExceptionQuandoMoradorNaoEncontrado() {
		
		Long invalidId = 99L;
		
		when(rep.findById(invalidId)).thenReturn(Optional.empty());
		
		NotFoundObjectException exception = assertThrows(NotFoundObjectException.class, () -> service.getMoradorById(invalidId));
		
		assertEquals("O id: 99 não se encontra na base de dados!", exception.getMessage());
	}
	

}
