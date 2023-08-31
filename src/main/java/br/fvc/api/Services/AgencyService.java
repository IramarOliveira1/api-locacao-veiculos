package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Agency.AgencyRequestDTO;
import br.fvc.api.Domain.Agency.AgencyResponseDTO;
import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Models.Address;
import br.fvc.api.Models.Agency;
import br.fvc.api.Repositories.AddressRepository;
import br.fvc.api.Repositories.AgencyRepository;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

      @Autowired
    private AddressRepository addressRepository;

    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }

    public ResponseEntity<Object> store(AgencyRequestDTO data) {
        try {
            if (agencyRepository.existsByNome(data.nome)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Agência já existe!"));
            }
            if (agencyRepository.existsByTelefone(data.telefone)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Telefone já existe!"));
            }

            Address address = new Address(data.address);

            addressRepository.save(address);

            Agency agency = new Agency(data);

            agency.setAddress(address);

            agencyRepository.save(agency);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Agência cadastrada com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filter(AgencyRequestDTO data) {
        List<AgencyResponseDTO> agency = this.agencyRepository.findByNome(data.nome).stream()
           .map(AgencyResponseDTO::new).toList();

        if(agency.isEmpty()){
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Agência não encontrada"));
        }

        return ResponseEntity.status(200).body(agency);
    }

}
