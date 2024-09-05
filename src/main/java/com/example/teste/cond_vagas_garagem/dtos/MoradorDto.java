package com.example.teste.cond_vagas_garagem.dtos;

import java.util.Objects;

public class MoradorDto {
	
	private String nomeDoMorador;
	
	private String apartamento;
	
	private String bloco;
	
	public MoradorDto() {
	}

	public MoradorDto(String nomeDoMorador, String apartamento, String bloco) {
		this.nomeDoMorador = nomeDoMorador;
		this.apartamento = apartamento;
		this.bloco = bloco;
	}


	public String getNomeDoMorador() {
		return nomeDoMorador;
	}

	public void setNomeDoMorador(String nomeDoMorador) {
		this.nomeDoMorador = nomeDoMorador;
	}

	public String getApartamento() {
		return apartamento;
	}

	public void setApartamento(String apartamento) {
		this.apartamento = apartamento;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(apartamento, bloco, nomeDoMorador);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoradorDto other = (MoradorDto) obj;
		return Objects.equals(apartamento, other.apartamento) && Objects.equals(bloco, other.bloco)
				&& Objects.equals(nomeDoMorador, other.nomeDoMorador);
	}

	@Override
	public String toString() {
		return "MoradorDto [nomeDoMorador=" + nomeDoMorador + ", apartamento=" + apartamento + ", bloco=" + bloco + "]";
	}
}
