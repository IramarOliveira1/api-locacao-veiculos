package br.fvc.api.Domain.User;

import br.fvc.api.Domain.Address.AddressDTO;
import br.fvc.api.Models.Address;

public class UserDTO {
    public String name;
    public String email;
    public String cpf;
    public UserRole role;
    public String password;
    public String phone;
    public AddressDTO address;
}