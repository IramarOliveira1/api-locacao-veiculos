package br.fvc.api.Domain.Agency;

import br.fvc.api.Models.Address;
import br.fvc.api.Models.Agency;

public class AgencyResponseDTO {
    public Long id;
    public String nome;
    public String telefone;
    public String quantidade_total;
    public Address address;

    public AgencyResponseDTO(Agency agency){
        this.id = agency.getId();
        this.nome = agency.getNome();
        this.telefone = agency.getTelefone();
        this.quantidade_total = agency.getQuantidade_total();
        this.address = agency.getAddress();

    }
}
