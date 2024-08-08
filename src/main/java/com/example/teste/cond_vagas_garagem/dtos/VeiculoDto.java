package com.example.teste.cond_vagas_garagem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoDto {
    private String placaDoVeiculo;
    private String marcaDoVeiculo;
    private String corDoVeiculo;
    private String moradorId;
    private String vagaDeGaragemId;
}

