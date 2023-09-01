package br.fvc.api.Services;

import java.util.List;

import org.apache.catalina.connector.Response;
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

        if (agency.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Agência não encontrada"));
        }

        return ResponseEntity.status(200).body(agency);
    }

    public ResponseEntity<Object> update(Long id, Agency data) {
        try {
            Agency agency = agencyRepository.findById(id).get();

            if (data.getNome().equals(agency.getNome()) && agencyRepository.existsByNome(data.getNome())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Agência já existe!"));
            }

            if (data.getTelefone().equals(agency.getTelefone())
                    && agencyRepository.existsByTelefone(data.getTelefone())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Telefone já cadastrado!"));
            }

            agency.setNome(data.getNome());
            agency.setTelefone(data.getTelefone());
            agency.setQuantidade_total(data.getQuantidade_total());
            agency.getAddress().setBairro(data.address.getBairro());
            agency.getAddress().setCep(data.address.getCep());
            agency.getAddress().setCidade(data.address.getCidade());
            agency.getAddress().setComplemento(data.address.getComplemento());
            agency.getAddress().setLogradouro(data.address.getLogradouro());
            agency.getAddress().setNumero(data.address.getNumero());
            agency.getAddress().setUf(data.address.getUf().toUpperCase());

            agencyRepository.save(agency);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Agência atualizada!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {

            agencyRepository.deleteById(id);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Agência deletada!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}
