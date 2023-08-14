package br.fvc.api.Domain.User;

import br.fvc.api.Domain.Address.AddressDTO;
import br.fvc.api.Models.User;

public class UserRequestDTO {
    public String name;
    public String email;
    public String cpf;
    public UserRole role;
    public String password;
    public String phone;
    public AddressDTO address;
}