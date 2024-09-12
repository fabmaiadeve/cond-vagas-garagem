package com.example.teste.cond_vagas_garagem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.teste.cond_vagas_garagem.dtos.VagaDto;
import com.example.teste.cond_vagas_garagem.exceptions.ConstraintViolationException;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
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
	private VagaDto uptVagaDto;
	
	@BeforeEach
	public void setUp() {
		validMorador = new Morador("MoradorSuccess", "apSuccess", "blocoSucess");
		validVagaDto = new VagaDto("c100", false, null, 1L);
		validVaga = new Vaga("c100", false, null, validMorador);
		existingVaga = new Vaga("Vaga 102", false, null, validMorador);
		invalidVagaDto = new VagaDto("", null, null, 1L);
		uptVagaDto = new VagaDto("c100update", true, 2L, 1L);
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
	
	@Test
	public void testDeveRetornarVagaPorIdComSucesso() {
		
		validVaga.setId(1L);
		Optional<Vaga> expectedVagaOpt = Optional.of(validVaga);
		
		when(rep.findById(validVaga.getId())).thenReturn(expectedVagaOpt);
		
		Vaga savedVaga = service.getVagaById(validVaga.getId());
		
		assertEquals(expectedVagaOpt.get(), savedVaga);
	}
	
	@Test
	public void testDeveRetornarExceptionQuandoVagaNaoEncontrada() {
		
		Long invalidId = 99L;
		
		when(rep.findById(invalidId)).thenReturn(Optional.empty());
		
		NotFoundObjectException exception = assertThrows(NotFoundObjectException.class, () -> service.getVagaById(invalidId));
		
		assertEquals("O id: 99 não se encontra na base de dados!", exception.getMessage());
	}
	
	@Test
	public void testDeveAlterarVagaComSucesso() {		
		
		Long validId = 1L; 
		Long moradorId = 1L;
		  
		when(rep.findById(validId)).thenReturn(Optional.of(existingVaga));
		  
		when(serviceMorador.getMoradorById(moradorId)).thenReturn(validMorador);
		  
		when(rep.save(any(Vaga.class))).thenReturn(existingVaga);
		  
		uptVagaDto.setMoradorId(moradorId);
		  
		Vaga updatedVaga = service.updateVagaById(validId, uptVagaDto);
		  
		assertNotNull(updatedVaga); 
		assertEquals(uptVagaDto.getNumeroDaVaga(), updatedVaga.getNumeroDaVaga());		
	}
	
	@Test
	public void testNaoDeveAlterarVagaPorId() {
		
		Long invalidId = 99L;
		
		VagaDto vagaDto = new VagaDto("c100", false, null, 1L);
		
		when(rep.findById(invalidId)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundObjectException.class, () -> service.updateVagaById(invalidId, vagaDto));
		
		verify(rep).findById(invalidId);
		verify(rep, never()).save(any(Vaga.class));
	}
	
	@Test
	public void testDeveDeletarVagaComSucesso() {
		
		Long validId = 1L;
		
		Vaga vagaSemVeiculo = new Vaga("c100", false, null, validMorador);
		vagaSemVeiculo.setId(validId);
		vagaSemVeiculo.setVeiculo(null);
		
		when(rep.findById(validId)).thenReturn(Optional.of(vagaSemVeiculo));
		
		service.deleteVagaById(validId);
		
		verify(rep).findById(validId);
		verify(rep).deleteById(validId);		
	}
	
	@Test
	public void testDeveLancarExceptionQuandoNaoEncontrarVaga() {
	    Long invalidId = 99L;

	    when(rep.findById(invalidId)).thenReturn(Optional.empty());

	    assertThrows(NotFoundObjectException.class, () -> service.deleteVagaById(invalidId));

	    verify(rep, never()).deleteById(invalidId);
	}
	
	@Test
	public void testDeveLancarConstraintViolationExceptionAoTentarDeletarVagaComVeiculo() {
	    Long validId = 1L;
	    Vaga vagaComVeiculo = new Vaga("Vaga 102", false, null, validMorador);
	    vagaComVeiculo.setVeiculo(new Veiculo("ABC-1234", "Modelo X", "Cor Y", validMorador, validVaga));

	    when(rep.findById(validId)).thenReturn(Optional.of(vagaComVeiculo));

	    assertThrows(ConstraintViolationException.class, () -> service.deleteVagaById(validId));

	    verify(rep, never()).deleteById(validId);
	}

}
