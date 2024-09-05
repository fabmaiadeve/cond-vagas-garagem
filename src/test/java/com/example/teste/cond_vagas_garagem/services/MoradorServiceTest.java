package com.example.teste.cond_vagas_garagem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.teste.cond_vagas_garagem.dtos.MoradorDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.repositories.MoradorRepository;

@ExtendWith(MockitoExtension.class)
class MoradorServiceTest {
	
	@Mock private MoradorRepository rep;
	@InjectMocks private MoradorService service;
	
	private MoradorDto validMoradorDto;

	@BeforeEach
	void setUp() {
		validMoradorDto = new MoradorDto("MoradorSuccess", "apSuccess", "blocoSucess");
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
	
	

}
