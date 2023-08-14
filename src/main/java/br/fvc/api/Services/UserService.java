package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.User.UserRequestDTO;
import br.fvc.api.Domain.User.UserResponseDTO;
import br.fvc.api.Models.Address;
import br.fvc.api.Models.User;
import br.fvc.api.Repositories.AddressRepository;
import br.fvc.api.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<Object> findAll() {
        return ResponseEntity.status(200).body("users");
    }

    public ResponseEntity<Object> store(UserRequestDTO data) {
        try {

            if (userRepository.existsByEmail(data.email)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Email já existe!"));
            }
            if (userRepository.existsByCpf(data.cpf)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "CPF já existe!"));
            }
            if (userRepository.existsByTelefone(data.phone)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Telefone já existe!"));
            }

            Address address = new Address(data.address);

            addressRepository.save(address);

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password);

            User user = new User(data, encryptedPassword);

            user.setAddress(address);

            userRepository.save(user);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Usuário cadastrado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}
