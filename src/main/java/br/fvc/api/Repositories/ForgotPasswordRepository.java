package br.fvc.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.models.ForgotPassword;
import br.fvc.api.models.User;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    ForgotPassword findByCode(Long code);

    ForgotPassword findByUser(User user);
}