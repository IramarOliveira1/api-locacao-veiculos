package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Models.Agency;
import br.fvc.api.Repositories.AgencyRepository;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }

    public ResponseEntity<Object> store(Agency data) {
        try {
            if (agencyRepository.existsByNome(data.getNome())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Agência já existe!"));
            }
            if (agencyRepository.existsByTelefone(data.getTelefone())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Telefone já existe!"));
            }

            Agency agency = new Agency();

            agency.setNome(data.getNome());
            agency.setQuantidade_total(data.getQuantidade_total());
            agency.setTelefone(data.getTelefone());
            agency.setAddress(data.getAddress());

            agencyRepository.save(agency);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Agência cadastrada com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}
