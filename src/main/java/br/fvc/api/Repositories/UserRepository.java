package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import br.fvc.api.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    @Query("SELECT u FROM usuario u WHERE u.email = :email")
    User findSendMail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByTelefone(String telefone);

    Boolean existsByCpf(String cpf);

    @Query("SELECT u FROM usuario u WHERE u.nome LIKE %:name% OR u.cpf LIKE %:cpf% ")
    List<User> findNameOrCpf(String name, String cpf);
}