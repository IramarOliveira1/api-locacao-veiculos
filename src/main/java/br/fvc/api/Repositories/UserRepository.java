package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.fvc.api.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByTelefone(String telefone);

    Boolean existsByCpf(String cpf);
}