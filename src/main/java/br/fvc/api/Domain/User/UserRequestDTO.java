package br.fvc.api.Domain.User;

import br.fvc.api.Domain.Address.AddressDTO;

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