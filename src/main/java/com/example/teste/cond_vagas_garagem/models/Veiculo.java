package com.example.teste.cond_vagas_garagem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_veiculo")
public class Veiculo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String placaDoVeiculo;
	
	private String modeloDoVeiculo;
	
	private String corDoVeiculo;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "morador_id", referencedColumnName = "id")
	private Morador morador;
	
	@OneToOne
	@JoinColumn(name = "vaga_id", referencedColumnName = "id", unique = true)
	private Vaga vaga;
	
	public Veiculo() {
	}

	public Veiculo(String placaDoVeiculo, String modeloDoVeiculo, String corDoVeiculo, Morador morador, Vaga vaga) {
		super();
		this.placaDoVeiculo = placaDoVeiculo;
		this.modeloDoVeiculo = modeloDoVeiculo;
		this.corDoVeiculo = corDoVeiculo;
		this.morador = morador;
		this.vaga = vaga;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Morador getMorador() {
		return morador;
	}

	public void setMorador(Morador morador) {
		this.morador = morador;
	}	

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	@Override
	public String toString() {
		return "Veiculo [id=" + id + ", placaDoVeiculo=" + placaDoVeiculo + ", modeloDoVeiculo=" + modeloDoVeiculo
				+ ", corDoVeiculo=" + corDoVeiculo + ", morador=" + morador + ", vaga=" + vaga + "]";
	}
}
