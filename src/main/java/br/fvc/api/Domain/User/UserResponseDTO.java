package br.fvc.api.Domain.User;

import java.util.List;

import br.fvc.api.Domain.Address.AddressDTO;
import br.fvc.api.Models.Address;
import br.fvc.api.Models.User;

public class UserResponseDTO {

    public String name;
    public String email;
    public String cpf;
    public UserRole role;
    public String password;
    public String phone;
    public AddressDTO address;

    public UserResponseDTO(User user) {
        this.name = user.getNome();
        this.email = user.getEmail();
        // this.name = user.getNome();
        // this.name = user.getNome();
        // this.name = user.getNome();
    }
}
