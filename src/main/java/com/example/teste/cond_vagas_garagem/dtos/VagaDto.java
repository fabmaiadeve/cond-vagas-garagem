package com.example.teste.cond_vagas_garagem.dtos;

import java.util.Objects;

public class VagaDto {
	
	private String numeroDaVaga;
	
	private Boolean ehAlugada;
	
	private Long moradorQueAlugou;
	
	private Long moradorId;
	
	
	public VagaDto() {
	}

	public VagaDto(String numeroDaVaga, Boolean ehAlugada, Long moradorQueAlugou, Long moradorId) {
		super();
		this.numeroDaVaga = numeroDaVaga;
		this.ehAlugada = ehAlugada;
		this.moradorQueAlugou = moradorQueAlugou;
		this.moradorId = moradorId;
	}

	public String getNumeroDaVaga() {
		return numeroDaVaga;
	}

	public void setNumeroDaVaga(String numeroDaVaga) {
		this.numeroDaVaga = numeroDaVaga;
	}

	public Boolean getEhAlugada() {
		return ehAlugada;
	}

	public void setEhAlugada(Boolean ehAlugada) {
		this.ehAlugada = ehAlugada;
	}

	public Long getMoradorQueAlugou() {
		return moradorQueAlugou;
	}

	public void setMoradorQueAlugou(Long moradorQueAlugou) {
		this.moradorQueAlugou = moradorQueAlugou;
	}

	public Long getMoradorId() {
		return moradorId;
	}

	public void setMoradorId(Long moradorId) {
		this.moradorId = moradorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ehAlugada, moradorId, moradorQueAlugou, numeroDaVaga);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VagaDto other = (VagaDto) obj;
		return Objects.equals(ehAlugada, other.ehAlugada) && Objects.equals(moradorId, other.moradorId)
				&& Objects.equals(moradorQueAlugou, other.moradorQueAlugou)
				&& Objects.equals(numeroDaVaga, other.numeroDaVaga);
	}

	@Override
	public String toString() {
		return "VagaDto [numeroDaVaga=" + numeroDaVaga + ", ehAlugada=" + ehAlugada + ", moradorQueAlugou="
				+ moradorQueAlugou + ", moradorId=" + moradorId + "]";
	}
}
