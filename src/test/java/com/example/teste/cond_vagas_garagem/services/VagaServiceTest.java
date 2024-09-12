package com.example.teste.cond_vagas_garagem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.teste.cond_vagas_garagem.dtos.VagaDto;
import com.example.teste.cond_vagas_garagem.exceptions.ConstraintViolationException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.repositories.VagaRepository;

@ExtendWith(MockitoExtension.class)
class VagaServiceTest {

	@Mock private VagaRepository rep;
	@Mock private MoradorService serviceMorador;
	@InjectMocks private VagaService service;
	
	private VagaDto validVagaDto;
	private Vaga validVaga;
	private Morador validMorador;
	private Vaga existingVaga;
	private VagaDto invalidVagaDto;
	
	@BeforeEach
	public void setUp() {
		validMorador = new Morador("MoradorSuccess", "apSuccess", "blocoSucess");
		validVagaDto = new VagaDto("c100", false, null, 1L);
		validVaga = new Vaga("c100", false, null, validMorador);
		existingVaga = new Vaga("Vaga 102", false, null, new Morador());
		invalidVagaDto = new VagaDto("", null, null, 1L); 

	}

	@Test
	public void testDeveSalvarVagaComSucesso() {
		
		Long moradorId = 1L;
		
		validMorador.setId(moradorId);
		
		Vaga expectedVaga = new Vaga(
				validVaga.getNumeroDaVaga(),
				validVaga.getEhAlugada(),
				validVaga.getMoradorQueAlugou(),
				validVaga.getMorador());
		
		VagaDto expectedVagaDto = new VagaDto(
				expectedVaga.getNumeroDaVaga(),
				expectedVaga.getEhAlugada(),
				expectedVaga.getMoradorQueAlugou(),
				expectedVaga.getMorador().getId());
		
		when(rep.findByMoradorId(moradorId)).thenReturn(Optional.empty());
		
		when(serviceMorador.getMoradorById(moradorId)).thenReturn(validMorador);
		
		when(rep.save(any())).thenReturn(expectedVaga);
		
		VagaDto savedVagaDto = service.saveVaga(validVagaDto);
		
		assertNotNull(savedVagaDto);
		assertEquals(expectedVagaDto, savedVagaDto);
	}
	
	@Test
	public void testNaoDeveSalvarVagaQuandoMoradorJaTemVaga() {
		
		Long moradorId = 1L;
		
		when(rep.findByMoradorId(moradorId)).thenReturn(Optional.of(existingVaga));
		
		assertThrows(ConstraintViolationException.class, () -> service.saveVaga(validVagaDto));
		
		verify(rep).findByMoradorId(moradorId);
        verify(rep, never()).save(any(Vaga.class));		
	}
	
	@Test
	public void testDeveLancarExceptionQuandoCamposEstaoNulos() {
		
		Long moradorId = 1L;
		
		when(rep.findByMoradorId(moradorId)).thenReturn(Optional.empty());
		
		validMorador.setId(moradorId);
		
		when(serviceMorador.getMoradorById(moradorId)).thenReturn(validMorador);
		
		assertThrows(NotNullableFieldsException.class, () -> service.saveVaga(invalidVagaDto));

        verify(rep).findByMoradorId(moradorId);
        verify(rep, never()).save(any(Vaga.class));
	}

}
