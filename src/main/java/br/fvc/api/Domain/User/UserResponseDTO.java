package br.fvc.api.Domain.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.fvc.api.Models.Address;
import br.fvc.api.Models.User;

public class UserResponseDTO {

    public Long id;
    public String name;
    public String email;

    @JsonInclude(Include.NON_NULL)
    public String cpf;
    public String role;

    @JsonInclude(Include.NON_NULL)
    public String phone;

    @JsonInclude(Include.NON_NULL)
    public Address address;
    
    @JsonInclude(Include.NON_NULL)
    public String token;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getNome();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.role = user.getRole();
        this.phone = user.getTelefone();
        this.address = user.getAddress();
    }

    public UserResponseDTO(User user, String token) {
        this.id = user.getId();
        this.name = user.getNome();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
