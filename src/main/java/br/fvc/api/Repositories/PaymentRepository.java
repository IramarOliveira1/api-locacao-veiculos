package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.Models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
