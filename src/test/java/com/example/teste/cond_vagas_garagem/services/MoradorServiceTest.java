package com.example.teste.cond_vagas_garagem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.teste.cond_vagas_garagem.dtos.MoradorDto;
import com.example.teste.cond_vagas_garagem.exceptions.ConstraintViolationException;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.repositories.MoradorRepository;

@ExtendWith(MockitoExtension.class)
class MoradorServiceTest {
	
	@Mock private MoradorRepository rep;
	@InjectMocks private MoradorService service;
	
	private MoradorDto validMoradorDto;
	private Morador validMorador;
	private MoradorDto uptMoradorDto;
	private Morador existingMorador;

	@BeforeEach
	void setUp() {
		validMoradorDto = new MoradorDto("MoradorSuccess", "apSuccess", "blocoSucess");
		validMorador = new Morador("MoradorSuccess", "apSuccess", "blocoSucess");
		uptMoradorDto = new MoradorDto("UpdateMoradorSuccess", "UpdateApSuccess", "UpdateBlocoSucess");
		existingMorador = new Morador("ExistingMoradorSuccess", "ExistingApSuccess", "ExistingBlocoSucess");
	}
	
	@Test
	public void testDeveSalvarMoradorComSucesso() {
		
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
	public void testDeveLancarExceptionQuandoNomeDoMoradorEstaNulo() { 
		 
		validMoradorDto.setNomeDoMorador(null);
		
		NotNullableFieldsException exception = assertThrows(NotNullableFieldsException.class, () -> service.saveMorador(validMoradorDto));
		assertEquals("O campo do nome do morador não pode ser nulo ou vazio!", exception.getMessage());
	}
	
	@Test 
	public void testDeveLancarExceptionQuandoApartamentoEstaNulo() { 
		
		validMoradorDto.setApartamento(null);
		
		NotNullableFieldsException exception = assertThrows(NotNullableFieldsException.class, () -> service.saveMorador(validMoradorDto));
		assertEquals("O campo de apartamento não pode ser nulo ou vazio!", exception.getMessage());
	}
	
	@Test 
	public void testDeveLancarExceptionQuandoBlocoEstaNulo() { 
		
		validMoradorDto.setBloco(null);
		
		NotNullableFieldsException exception = assertThrows(NotNullableFieldsException.class, () -> service.saveMorador(validMoradorDto));
		assertEquals("O campo de bloco não pode ser nulo ou vazio!", exception.getMessage());
	}
	
	@Test
	public void testDeveRetornarMoradorPorIdComSucesso() {
		
		validMorador.setId(1L);
		Optional<Morador> expectedMoradorOpt = Optional.of(validMorador);
		
		when(rep.findById(validMorador.getId())).thenReturn(expectedMoradorOpt);
		
		Morador savedMorador = service.getMoradorById(validMorador.getId());
		
		assertEquals(expectedMoradorOpt.get(), savedMorador);
	}
	
	@Test
	public void testDeveRetornarExceptionQuandoMoradorNaoEncontrado() {
		
		Long invalidId = 99L;
		
		when(rep.findById(invalidId)).thenReturn(Optional.empty());
		
		NotFoundObjectException exception = assertThrows(NotFoundObjectException.class, () -> service.getMoradorById(invalidId));
		
		assertEquals("O id: 99 não se encontra na base de dados!", exception.getMessage());
	}
	
	@Test
	public void testDeveAlterarMoradorComSucesso() {
		
		Long validId = 1L;
		
		when(rep.findById(validId)).thenReturn(Optional.of(existingMorador));
		
		when(rep.save(any(Morador.class))).thenReturn(existingMorador);
		
		Morador updatedMorador = service.updateMoradorById(validId, uptMoradorDto);
		
		assertNotNull(updatedMorador);
		assertEquals(uptMoradorDto.getNomeDoMorador(), updatedMorador.getNomeDoMorador());	
	}
	
	@Test
	public void testNaoDeveAlterarMoradorPorId() {
        
		Long invalidId = 99L;
        
		MoradorDto moradorDto = new MoradorDto("Nome Atualizado", "Apto 101", "Bloco B");

        when(rep.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(NotFoundObjectException.class, () -> service.updateMoradorById(invalidId, moradorDto));

        verify(rep).findById(invalidId);
        verify(rep, never()).save(any(Morador.class));
    }
	
	@Test
	public void testDeveDeletarMoradorComSucesso() {
		
		Long validId = 1L;
        
		Morador morador = new Morador("Nome Morador", "Apto 101", "Bloco A");
		
		morador.setId(validId);
		morador.setVeiculos(Collections.emptyList());
		morador.setVaga(null);
		
		when(rep.findById(validId)).thenReturn(Optional.of(morador));
		
		service.deleteMoradorById(validId);
		
		verify(rep).findById(validId);
		verify(rep).deleteById(validId);
	} 
	
	@Test
	public void testNaoDeveDeletarMoradorQuandoIdNaoEncontrado() {
		
		Long invalidId = 99L;
		
		when(rep.findById(invalidId)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundObjectException.class, () -> service.deleteMoradorById(invalidId));
		
		verify(rep).findById(invalidId);
		verify(rep, never()).deleteById(anyLong());
	}
	
	@Test
	public void testNaoDeveDeletarMoradorPorquePossuiRegistroDeOutrasEntidades() {
		
		Long validId = 1L;
        
		Morador morador = new Morador("Nome Morador", "Apto 101", "Bloco A");

		morador.setId(validId);
		morador.setVeiculos(Collections.singletonList(new Veiculo()));
		
		when(rep.findById(validId)).thenReturn(Optional.of(morador));
		
		assertThrows(ConstraintViolationException.class, () -> service.deleteMoradorById(validId));
		
		verify(rep).findById(validId);
		verify(rep, never()).deleteById(anyLong());		
	}

}
