package com.example.teste.cond_vagas_garagem.models;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tb_veiculo")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVeiculo;
    private String placaDoVeiculo;
    private String marcaDoVeiculo;
    private String corDoVeiculo;
    private Long moradorId;
    private Long vagaDeGaragemId;

    public Veiculo(VeiculoDto veiculoDto) {
        this.placaDoVeiculo = veiculoDto.getPlacaDoVeiculo();
        this.marcaDoVeiculo = veiculoDto.getMarcaDoVeiculo();
        this.corDoVeiculo = veiculoDto.getCorDoVeiculo();
        this.moradorId = Long.valueOf(veiculoDto.getMoradorId());
        this.vagaDeGaragemId = Long.valueOf(veiculoDto.getVagaDeGaragemId());
    }
}
