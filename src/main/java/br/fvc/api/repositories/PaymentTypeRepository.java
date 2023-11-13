package br.fvc.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.models.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    Boolean existsByNome(String nome);

    @Query("SELECT tp FROM tipo_pagamento tp WHERE tp.nome LIKE %:nome% ")
    List<PaymentType> findTypePayment(String nome);
}