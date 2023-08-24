package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.Models.ForgotPassword;
import br.fvc.api.Models.User;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    ForgotPassword findByCode(Long code);

    ForgotPassword findByUser(User user);
}