package com.example.teste.cond_vagas_garagem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Morador;
import com.example.teste.cond_vagas_garagem.models.Vaga;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.repositories.VeiculoRepository;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock private VeiculoRepository rep;
    @Mock private MoradorService moradorService;
    @Mock private VagaService vagaService;

    @InjectMocks private VeiculoService service;

    private VeiculoDto validVeiculoDto;
    private Morador validMorador;
    private Vaga validVaga;
    private Veiculo validVeiculo;
    private Veiculo existingVeiculo;
    private VeiculoDto uptVeiculoDto;

    @BeforeEach
    void setUp() {
        validMorador = new Morador("Morador1", "Apt1", "Bloco1");
        validVaga = new Vaga("Vaga101", false, null, validMorador);
        validVeiculoDto = new VeiculoDto("ABC-1234", "ModeloX", "Azul", 1L, 1L);
        validVeiculo = new Veiculo("ABC-1234", "ModeloX", "Azul", validMorador, validVaga);
        existingVeiculo = new Veiculo("dfg-5678", "ModeloK", "Azul", validMorador, validVaga);
        uptVeiculoDto = new VeiculoDto("mudaABC-1234", "mudaModeloX", "mudaAzul", 2L, 3L);
    }

    @Test
    public void testDeveSalvarVeiculoComSucesso() {
        
    	validMorador.setId(1L);
    	validVaga.setId(1L);
    	
    	// Simula a busca de morador e vaga com sucesso
        when(moradorService.getMoradorById(1L)).thenReturn(validMorador);
        when(vagaService.getVagaById(1L)).thenReturn(validVaga);
        when(rep.save(any(Veiculo.class))).thenReturn(validVeiculo);

        // Executa o método saveVeiculo
        VeiculoDto savedVeiculoDto = service.saveVeiculo(validVeiculoDto);

        // Verifica se o veículo foi salvo corretamente
        assertNotNull(savedVeiculoDto);
        assertEquals(validVeiculoDto.getPlacaDoVeiculo(), savedVeiculoDto.getPlacaDoVeiculo());
        verify(rep, times(1)).save(any(Veiculo.class));
    }

    @Test
    public void testDeveLancarNotFoundObjectExceptionSeMoradorNaoForEncontrado() {
        // Simula que o morador não foi encontrado
        when(moradorService.getMoradorById(1L)).thenThrow(new NotFoundObjectException("Morador não encontrado"));

        // Verifica se a exceção é lançada
        assertThrows(NotFoundObjectException.class, () -> service.saveVeiculo(validVeiculoDto));

        // Verifica que o repositório não tentou salvar
        verify(rep, never()).save(any(Veiculo.class));
    }

    @Test
    public void testDeveLancarNotFoundObjectExceptionSeVagaNaoForEncontrada() {
        // Simula que a vaga não foi encontrada
        when(moradorService.getMoradorById(1L)).thenReturn(validMorador);
        when(vagaService.getVagaById(1L)).thenThrow(new NotFoundObjectException("Vaga não encontrada"));

        // Verifica se a exceção é lançada
        assertThrows(NotFoundObjectException.class, () -> service.saveVeiculo(validVeiculoDto));

        // Verifica que o repositório não tentou salvar
        verify(rep, never()).save(any(Veiculo.class));
    }

    @Test
    public void testDeveLancarNotNullableFieldsExceptionQuandoPlacaDoVeiculoForNuloOuVazio() {
        // Simula um DTO inválido (placa vazia)
        VeiculoDto invalidDto = new VeiculoDto("", "ModeloX", "Azul", 1L, 1L);

        // Simula a busca do morador e da vaga
        when(moradorService.getMoradorById(1L)).thenReturn(validMorador);
        when(vagaService.getVagaById(1L)).thenReturn(validVaga);

        // Verifica se a exceção é lançada
        assertThrows(NotNullableFieldsException.class, () -> service.saveVeiculo(invalidDto));

        // Verifica que o repositório não tentou salvar
        verify(rep, never()).save(any(Veiculo.class));
    }

    @Test
    public void testDeveLancarNotNullableFieldsExceptionQuandoModeloDoVeiculoForNuloOuVazio() {
        // Simula um DTO inválido (modelo vazio)
        VeiculoDto invalidDto = new VeiculoDto("ABC-1234", "", "Azul", 1L, 1L);

        // Simula a busca do morador e da vaga
        when(moradorService.getMoradorById(1L)).thenReturn(validMorador);
        when(vagaService.getVagaById(1L)).thenReturn(validVaga);

        // Verifica se a exceção é lançada
        assertThrows(NotNullableFieldsException.class, () -> service.saveVeiculo(invalidDto));

        // Verifica que o repositório não tentou salvar
        verify(rep, never()).save(any(Veiculo.class));
    }
    
    @Test
    public void testDeveLancarNotNullableFieldsExceptionQuandoCorDoVeiculoForNuloOuVazio() {
        // Simula um DTO inválido (cor vazia)
        VeiculoDto invalidDto = new VeiculoDto("ABC-1234", "ModeloX", "", 1L, 1L);

        // Simula a busca do morador e da vaga
        when(moradorService.getMoradorById(1L)).thenReturn(validMorador);
        when(vagaService.getVagaById(1L)).thenReturn(validVaga);

        // Verifica se a exceção é lançada
        assertThrows(NotNullableFieldsException.class, () -> service.saveVeiculo(invalidDto));

        // Verifica que o repositório não tentou salvar
        verify(rep, never()).save(any(Veiculo.class));
    }
    
    @Test
	public void testDeveRetornarVeiculoPorIdComSucesso() {
    	
    	Long validId = 1L;
    	validVeiculo.setId(validId);
    	
    	Optional<Veiculo> expectedVeiculoOpt = Optional.of(validVeiculo);
    	
    	when(rep.findById(validId)).thenReturn(expectedVeiculoOpt);
    	
    	Veiculo savedVeiculo = service.getVeiculoById(validId);
    	
    	assertEquals(expectedVeiculoOpt.get(), savedVeiculo);
    }
    
    @Test
	public void testDeveRetornarExceptionQuandoVeiculoNaoEncontrado() {
    	
    	Long invalidId = 99L;
    	
    	when(rep.findById(invalidId)).thenReturn(Optional.empty());
    	
    	NotFoundObjectException exception = assertThrows(NotFoundObjectException.class, () -> service.getVeiculoById(invalidId));
    	
    	assertEquals("O id: 99 não se encontra na base de dados!", exception.getMessage());
    }
    
    @Test
	public void testDeveAlterarVeiculoComSucesso() {
    	
    	Long validId = 1L;
    	Long moradorId = 1L;
    	Long vagaId = 1L;
        
    	when(rep.findById(validId)).thenReturn(Optional.of(existingVeiculo));
    	
    	when(rep.save(any(Veiculo.class))).thenReturn(existingVeiculo);
    	
    	uptVeiculoDto.setMoradorId(moradorId);
    	uptVeiculoDto.setVagaId(vagaId);
    	
    	Veiculo updatedVeiculo = service.updateVeiculoById(vagaId, uptVeiculoDto);
    	
    	assertNotNull(updatedVeiculo);
    	assertEquals(uptVeiculoDto.getModeloDoVeiculo(), updatedVeiculo.getModeloDoVeiculo());
    	
    	verify(rep, times(1)).save(any(Veiculo.class));
    }
    
    @Test
	public void testNaoDeveAlterarVeiculoPorId() {
    	
    	Long invalidId = 99L;
    	
    	when(rep.findById(invalidId)).thenReturn(Optional.empty());
    	
    	assertThrows(NotFoundObjectException.class, () -> service.updateVeiculoById(invalidId, uptVeiculoDto));
    	
    	verify(rep).findById(invalidId);
		verify(rep, never()).save(any(Veiculo.class));   	
    }
    
    @Test
    public void testDeveDeletarVeiculoComSucesso() {
        
    	Long validId = 1L;
    	
    	validVeiculo.setId(validId);

        when(rep.findById(validId)).thenReturn(Optional.of(validVeiculo));

        service.deleteVeiculoById(validId);

        verify(rep).findById(validId);
		verify(rep).deleteById(validId);
    }
    
    @Test
    public void testDeveLancarExcecaoQuandoVeiculoNaoForEncontrado() {
        
    	Long invalidId = 99L;

        when(rep.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(NotFoundObjectException.class, () -> service.deleteVeiculoById(invalidId));

        verify(rep, never()).deleteById(invalidId);
    }
}
