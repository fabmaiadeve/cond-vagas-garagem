package com.example.teste.cond_vagas_garagem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_vaga")
public class Vaga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String numeroDaVaga;
	
	private Boolean ehAlugada;
	
	private Long moradorQueAlugou;

	@OneToOne
	@JoinColumn(name = "morador_id", referencedColumnName = "id") 
	private Morador morador;

	private Long veiculoId;
	
	public Vaga() {
	}

	public Vaga(String numeroDaVaga, Boolean ehAlugada, Long moradorQueAlugou, Morador morador, Long veiculoId) {
		super();
		this.numeroDaVaga = numeroDaVaga;
		this.ehAlugada = ehAlugada;
		this.moradorQueAlugou = moradorQueAlugou;
		this.morador = morador;
		this.veiculoId = veiculoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Morador getMorador() {
		return morador;
	}

	public void setMorador(Morador morador) {
		this.morador = morador;
	}

	public Long getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Long veiculoId) {
		this.veiculoId = veiculoId;
	}

	@Override
	public String toString() {
		return "Vaga [id=" + id + ", numeroDaVaga=" + numeroDaVaga + ", ehAlugada=" + ehAlugada + ", moradorQueAlugou="
				+ moradorQueAlugou + ", morador=" + morador + ", veiculoId=" + veiculoId + "]";
	}
}
