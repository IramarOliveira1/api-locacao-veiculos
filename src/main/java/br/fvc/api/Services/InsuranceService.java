package br.fvc.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import br.fvc.api.dtos.generic.GenericResponseDTO;
import br.fvc.api.models.Insurance;
import br.fvc.api.repositories.InsuranceRepository;

@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    public ResponseEntity<Object> findAll() {
        return ResponseEntity.status(200).body(insuranceRepository.findAllInsuranceOrderByIdDesc());
    }

    public ResponseEntity<Object> store(Insurance data) {
        try {

            if (insuranceRepository.existsByNome(data.getNome())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Seguro j� existe!"));
            }

            String removeComma = data.getPreco().replace(",", "");
            String removePoint = removeComma.replace(".", "");

            StringBuilder addPoint = new StringBuilder(removePoint);
            addPoint.insert(removePoint.length() - 2, '.');

            Insurance insurance = new Insurance();

            insurance.setPreco(addPoint.toString());

            insurance.setNome(data.getNome().toUpperCase());

            insuranceRepository.save(insurance);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Seguro cadastrado com sucesso!"));
        } catch (

        Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> index(Long id) {
        Optional<Insurance> insurance = this.insuranceRepository.findById(id);

        return ResponseEntity.status(200).body(insurance);
    }

    public ResponseEntity<Object> filter(Insurance data) {
        List<Insurance> insurances = insuranceRepository.findTipoCobertura(data.getNome());

        if (insurances.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true,
                    "Nenhum seguro encontrado!"));
        }

        return ResponseEntity.status(200).body(insurances);
    }

    public ResponseEntity<Object> update(Long id, Insurance data) {
        try {

            Insurance insurance = insuranceRepository.findById(id).get();

            if (!data.getNome().equals(insurance.getNome().toUpperCase())
                    && insuranceRepository.existsByNome(data.getNome().toUpperCase())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Seguro já existe!"));
            }

            String removeComma = data.getPreco().replace(",", "");
            String removePoint = removeComma.replace(".", "");

            StringBuilder addPoint = new StringBuilder(removePoint);
            addPoint.insert(removePoint.length() - 2, '.');

            insurance.setPreco(addPoint.toString());
            insurance.setNome(data.getNome().toUpperCase());

            insuranceRepository.save(insurance);
            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Seguro atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {
            insuranceRepository.deleteById(id);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Seguro excluido com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }
}
