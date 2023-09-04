package br.fvc.api.Services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

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
        return insuranceRepository.findAllInsuranceOrderByIdDesc();
    }

    public ResponseEntity<Object> store(Insurance data) {
        try {

            if (insuranceRepository.existsByTipo(data.getTipo())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Seguro j· existe!"));
            }

            // String valorFormatado = NumberFormat.getCurrencyInstance().format(12005.11);
            // System.out.println(valorFormatado);

            String remove_1 = data.getPreco().replace(",", "");
            String remove_2 = remove_1.replace(".", "");

            StringBuilder stringBuilder = new StringBuilder(remove_2);
            stringBuilder.insert(remove_2.length() - 2, '.');

            Insurance insurance = new Insurance();

            insurance.setPreco(stringBuilder.toString());

            insurance.setTipo(data.getTipo().toUpperCase());

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
        List<Insurance> insurances = insuranceRepository.findTipoCobertura(data.getTipo());

        if (insurances.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true,
                    "Nenhum seguro encontrado!"));
        }

        return ResponseEntity.status(200).body(insurances);
    }

    public ResponseEntity<Object> update(Long id, Insurance data) {
        try {

            Insurance insurance = insuranceRepository.findById(id).get();

            if (!data.getTipo().equals(insurance.getTipo().toUpperCase())
                    && insuranceRepository.existsByTipo(data.getTipo().toUpperCase())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Seguro j√° existe!"));
            }

            insurance.setPreco(data.getPreco());
            insurance.setTipo(data.getTipo().toUpperCase());

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
