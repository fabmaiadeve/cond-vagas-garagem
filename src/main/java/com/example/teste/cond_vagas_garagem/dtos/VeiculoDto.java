package com.example.teste.cond_vagas_garagem.dtos;

public class VeiculoDto {
	
	private String placaDoVeiculo;
	
	private String modeloDoVeiculo;
	
	private String corDoVeiculo;
	
	private Long moradorId;
	
	private Long vagaId;
	
	public VeiculoDto() {
	}

	public VeiculoDto(String placaDoVeiculo, String modeloDoVeiculo, String corDoVeiculo, Long moradorId, Long vagaId) {
		super();
		this.placaDoVeiculo = placaDoVeiculo;
		this.modeloDoVeiculo = modeloDoVeiculo;
		this.corDoVeiculo = corDoVeiculo;
		this.moradorId = moradorId;
		this.vagaId = vagaId;
	}

	public String getPlacaDoVeiculo() {
		return placaDoVeiculo;
	}

	public void setPlacaDoVeiculo(String placaDoVeiculo) {
		this.placaDoVeiculo = placaDoVeiculo;
	}

	public String getModeloDoVeiculo() {
		return modeloDoVeiculo;
	}

	public void setModeloDoVeiculo(String modeloDoVeiculo) {
		this.modeloDoVeiculo = modeloDoVeiculo;
	}

	public String getCorDoVeiculo() {
		return corDoVeiculo;
	}

	public void setCorDoVeiculo(String corDoVeiculo) {
		this.corDoVeiculo = corDoVeiculo;
	}

	public Long getMoradorId() {
		return moradorId;
	}

	public void setMoradorId(Long moradorId) {
		this.moradorId = moradorId;
	}

	public Long getVagaId() {
		return vagaId;
	}

	public void setVagaId(Long vagaId) {
		this.vagaId = vagaId;
	}

	@Override
	public String toString() {
		return "VeiculoDto [placaDoVeiculo=" + placaDoVeiculo + ", modeloDoVeiculo=" + modeloDoVeiculo
				+ ", corDoVeiculo=" + corDoVeiculo + ", moradorId=" + moradorId + ", vagaId=" + vagaId + "]";
	}
}
