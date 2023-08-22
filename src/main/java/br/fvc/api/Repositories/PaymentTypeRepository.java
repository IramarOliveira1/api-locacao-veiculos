package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    Boolean existsByTipo(String tipo);

    @Query("SELECT tp FROM tipo_pagamento tp WHERE tp.tipo LIKE %:type% ")
    List<PaymentType> findTypePayment(String type);
}