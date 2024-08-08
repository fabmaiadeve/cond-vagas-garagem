package com.example.teste.cond_vagas_garagem.services;

import com.example.teste.cond_vagas_garagem.dtos.VeiculoDto;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;
import com.example.teste.cond_vagas_garagem.models.Veiculo;
import com.example.teste.cond_vagas_garagem.repositories.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public Veiculo saveVeiculo(VeiculoDto veiculoDto) {

        Veiculo veiculo = new Veiculo(veiculoDto);

        validateFields(veiculo);

        return veiculoRepository.save(veiculo);
    }

    public Optional<Veiculo> getVeiculoById(Long id) {

        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);

        if (veiculoOptional.isEmpty()) {
            throw new NotFoundObjectException("O id: " + id.toString() + " não se encontra na base de Dados");
        }
        return veiculoOptional;
    }

    @Transactional
    public Optional<Veiculo> updateVeiculoById(Long veiculoId, VeiculoDto veiculoDto) {

        Optional<Veiculo> updateVeiculo = this.getVeiculoById(veiculoId);

        if (updateVeiculo.isPresent()) {
            updateVeiculo.get().setPlacaDoVeiculo(veiculoDto.getPlacaDoVeiculo());
            updateVeiculo.get().setMarcaDoVeiculo(veiculoDto.getMarcaDoVeiculo());
            updateVeiculo.get().setCorDoVeiculo(veiculoDto.getCorDoVeiculo());
            updateVeiculo.get().setMoradorId(Long.valueOf(veiculoDto.getMoradorId()));
            updateVeiculo.get().setVagaDeGaragemId(Long.valueOf(veiculoDto.getVagaDeGaragemId()));
        }

        validateUpdateFields(updateVeiculo);

        updateVeiculo.get().setIdVeiculo(veiculoId);

        return veiculoRepository.saveVeiculo(updateVeiculo);
    }

    @Transactional
    public void deleteVeiculoById(Long idVeiculo) {
        veiculoRepository.deleteById(this.getVeiculoById(idVeiculo).get().getIdVeiculo());
    }

    private void validateUpdateFields(Optional<Veiculo> veiculo) {

        if (veiculo.get().getIdVeiculo() == null) {
            throw new NotNullableFieldsException("O veículo não pode ser atualizado pois seu idVeiculo é nulo!");
        }
    }

    private void validateFields(Veiculo veiculo) {
        if ((veiculo.getIdVeiculo() == null) && (veiculo.getMoradorId() == null) && (veiculo.getVagaDeGaragemId() == null)) {
            throw new NotNullableFieldsException("O campo idVeiculo, idMorador, idVagaDeGaragem do veículo é nulo");
        }
    }

}
