package com.example.teste.cond_vagas_garagem.controllers;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.services.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<Veiculo> _addVeiculo(@RequestBody @Valid VeiculoDto veiculoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.saveVeiculo(veiculoDto));
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<Veiculo> _getVeiculoById(@PathVariable(value = "veiculoId") Long veiculoId) {
        Optional<Veiculo> veiculoOptional = veiculoService.getVeiculoById(veiculoId);
        return ResponseEntity.status(HttpStatus.OK).body(veiculoOptional.get());
    }


    @PutMapping("/{veiculoId}")
    public ResponseEntity<Optional<Veiculo>> _updateVeiculoById(@PathVariable(value = "veiculoId") Long veiculoId, @Valid @RequestBody VeiculoDto veiculoDto) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(veiculoService.updateVeiculoById(veiculoId, veiculoDto));
    }

    @DeleteMapping("/{veiculoId}")
    public ResponseEntity<Void> _deleteVagaById(@PathVariable(value = "veiculoId") Long veiculoId) {
        return ResponseEntity.noContent().build();
    }
}
