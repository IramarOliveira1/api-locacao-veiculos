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

    @Query("SELECT DISTINCT u FROM usuario u JOIN FETCH u.address WHERE u.role = :role ORDER BY u.id DESC")
    List<User> findAllRoleOrderByIdDesc(String role);

    Boolean existsByEmail(String email);

    Boolean existsByTelefone(String telefone);

    Boolean existsByCpf(String cpf);

    @Query("SELECT u FROM usuario u JOIN FETCH u.address WHERE (u.nome LIKE %:nameOrCpf% OR u.cpf LIKE %:nameOrCpf%) AND u.role = :role ")
    List<User> findNameOrCpf(String nameOrCpf, String role);
}