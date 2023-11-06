package br.fvc.api.dtos.user;

import br.fvc.api.dtos.address.AddressDTO;

public class UserRequestDTO {
    public String name;
    public String email;
    public String cpf;
    public String role = "USER";
    public String password;
    public String phone;
    public AddressDTO address;
    public Long code;
}