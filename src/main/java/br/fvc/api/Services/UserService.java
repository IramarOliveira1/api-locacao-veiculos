package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.User.LoginRequestDTO;
import br.fvc.api.Domain.User.LoginResponseDTO;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<Object> all() {
        List<UserResponseDTO> users = this.userRepository.findAll().stream().map(UserResponseDTO::new).toList();
        return ResponseEntity.status(200).body(users);
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

    public ResponseEntity<Object> login(LoginRequestDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email, data.senha);

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.status(200).body(new LoginResponseDTO((User) auth.getPrincipal(), token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> index(Long id) {
        List<UserResponseDTO> user = this.userRepository.findById(id).stream().map(UserResponseDTO::new).toList();
        return ResponseEntity.status(200).body(user);
    }

    public ResponseEntity<Object> filter(UserRequestDTO data) {
        List<UserResponseDTO> user = this.userRepository.findNameOrCpf(data.name, data.cpf).stream()
                .map(UserResponseDTO::new).toList();

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Nenhum cliente encontrado!"));
        }

        return ResponseEntity.status(200).body(user);
    }

    public ResponseEntity<Object> update(Long id, UserRequestDTO data) {
        try {
            User user = userRepository.findById(id).get();

            if (!data.cpf.equals(user.getCpf()) && userRepository.existsByCpf(data.cpf)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "CPF já existe!"));
            }
            if (!data.email.equals(user.getEmail()) && userRepository.existsByEmail(data.email)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Email já existe!"));
            }
            if (!data.phone.equals(user.getTelefone()) && userRepository.existsByTelefone(data.phone)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Telefone já existe!"));
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password);

            user.setNome(data.name);
            user.setEmail(data.email.toLowerCase());
            user.setCpf(data.cpf);
            user.setSenha(encryptedPassword);
            user.setTelefone(data.phone);
            user.getAddress().setBairro(data.address.neighborhood);
            user.getAddress().setCep(data.address.zipcode);
            user.getAddress().setCidade(data.address.city);
            user.getAddress().setComplemento(data.address.complement);
            user.getAddress().setLogradouro(data.address.address);
            user.getAddress().setNumero(data.address.number);
            user.getAddress().setUf(data.address.uf);

            userRepository.save(user);
            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Usu�rio atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {
            userRepository.deleteById(id);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Usuário excluido com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }
}
