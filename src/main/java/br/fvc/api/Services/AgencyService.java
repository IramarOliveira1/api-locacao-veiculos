package br.fvc.api.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Agency.AgencyRequestDTO;
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

    public ResponseEntity<Object> findAll() {
        return ResponseEntity.status(200).body(agencyRepository.findAllAgencyOrderByIdDesc());
    }

    public ResponseEntity<Object> store(AgencyRequestDTO data) {
        try {

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
        List<Agency> agency = this.agencyRepository.findByNome(data.nome);

        if (agency.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Agência não encontrada"));
        }

        return ResponseEntity.status(200).body(agency);
    }

    public ResponseEntity<Object> index(Long id) {
        Optional<Agency> agency = this.agencyRepository.findById(id);

        return ResponseEntity.status(200).body(agency);
    }

    public ResponseEntity<Object> update(Long id, AgencyRequestDTO data) {
        try {
            Agency agency = agencyRepository.findById(id).get();

            if (!data.telefone.equals(agency.getTelefone())
                    && agencyRepository.existsByTelefone(data.telefone)) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Telefone já cadastrado!"));
            }

            agency.setNome(data.nome);
            agency.setTelefone(data.telefone);
            agency.setQuantidade_total(data.quantidade_total);
            agency.getAddress().setBairro(data.address.neighborhood);
            agency.getAddress().setCep(data.address.zipcode);
            agency.getAddress().setCidade(data.address.city);
            agency.getAddress().setComplemento(data.address.complement);
            agency.getAddress().setLogradouro(data.address.address);
            agency.getAddress().setNumero(data.address.number);
            agency.getAddress().setUf(data.address.uf.toUpperCase());

            agencyRepository.save(agency);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Agência atualizada com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {

            agencyRepository.deleteById(id);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Agência excluida com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}
