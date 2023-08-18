package br.fvc.api.Domain.User;

import br.fvc.api.Models.Address;
import br.fvc.api.Models.User;

public class UserResponseDTO {

    public Long id;
    public String name;
    public String email;
    public String cpf;
    public String role;
    public String phone;
    public Address address;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getNome();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.role = user.getRole();
        this.phone = user.getTelefone();
        this.address = user.getAddress();
    }
}
