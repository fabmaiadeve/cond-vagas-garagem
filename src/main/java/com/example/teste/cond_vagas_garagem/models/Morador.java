package com.example.teste.cond_vagas_garagem.models;

public class Morador {
	
	private Long id;
	
	private String nomeDoMorador;
	
	private String apartamento;
	
	private String bloco;
	
	public Morador() {
	}

	public Morador(String nomeDoMorador, String apartamento, String bloco) {
		super();
		this.nomeDoMorador = nomeDoMorador;
		this.apartamento = apartamento;
		this.bloco = bloco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public String toString() {
		return "Morador [id=" + id + ", nomeDoMorador=" + nomeDoMorador + ", apartamento=" + apartamento + ", bloco="
				+ bloco + "]";
	}
}
