package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment WHERE user.id = :id ORDER BY reserve.id DESC")
    List<Reserve> findByIdReserve(Long id);

    @Query("SELECT r FROM reserva r WHERE r.id = :id")
    Reserve findByCodeReserve(Long id);

}
