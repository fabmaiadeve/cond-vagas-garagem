package com.example.teste.cond_vagas_garagem.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_morador")
public class Morador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomeDoMorador;
	
	private String apartamento;
	
	private String bloco;
	
	@OneToOne(mappedBy = "morador")
	@JsonManagedReference
	private Vaga vaga;
	
	@OneToMany(mappedBy = "morador")
	private List<Veiculo> veiculos = new ArrayList<>();
	
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

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@Override
	public String toString() {
		return "Morador [id=" + id + ", nomeDoMorador=" + nomeDoMorador + ", apartamento=" + apartamento + ", bloco="
				+ bloco + ", vaga=" + vaga + ", veiculos=" + veiculos + "]";
	}
}
