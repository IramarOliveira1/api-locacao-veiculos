package br.fvc.api.dtos.agency;

import br.fvc.api.dtos.address.AddressDTO;

public class AgencyRequestDTO {
    
    public String nome;
    public String telefone;
    public int quantidade_total;
    public AddressDTO address;
}
