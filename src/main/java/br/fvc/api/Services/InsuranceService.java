package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Models.Insurance;

import br.fvc.api.Repositories.InsuranceRepository;

@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    public List<Insurance> findAll() {
        return insuranceRepository.findAll();
    }

    public ResponseEntity<Object> store(Insurance data) {
        try {

            if (insuranceRepository.existsByTipo(data.getTipo())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Seguro já existe!"));
            }

            Insurance insurance = new Insurance();

            insurance.setPreco(data.getPreco());

            insurance.setTipo(data.getTipo().toUpperCase());

            insuranceRepository.save(insurance);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Seguro cadastrado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filter(Insurance data) {
        List<Insurance> insurances = insuranceRepository.findTipoCobertura(data.getTipo());

        if (insurances.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true,
                    "Nenhum cliente encontrado!"));
        }

        return ResponseEntity.status(200).body(insurances);
    }

    public ResponseEntity<Object> update(Long id, Insurance data) {
        try {

            Insurance insurance = insuranceRepository.findById(id).get();

            if (!data.getTipo().equals(insurance.getTipo()) && insuranceRepository.existsByTipo(data.getTipo())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Seguro já existe!"));
            }

            insurance.setPreco(data.getPreco());
            insurance.setTipo(data.getTipo().toUpperCase());

            insuranceRepository.save(insurance);
            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Seguro atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {
            insuranceRepository.deleteById(id);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Seguro excluido com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }
}
