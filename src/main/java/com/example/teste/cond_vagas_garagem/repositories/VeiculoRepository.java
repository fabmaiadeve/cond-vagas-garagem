package com.example.teste.cond_vagas_garagem.repositories;

import com.example.teste.cond_vagas_garagem.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Optional<Veiculo> saveVeiculo(Optional<Veiculo> veiculo);
}
