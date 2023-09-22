package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    // @Query("SELECT v FROM veiculo v JOIN FETCH v.modelo AS m WHERE m.nome LIKE
    // %:modeloOrMarca% or v.marca LIKE %:modeloOrMarca%")
    // @Query("SELECT DISTINCT v FROM veiculo v JOIN FETCH v.agencia JOIN FETCH
    // v.modelo ORDER BY v.id DESC")
    // NEW br.fvc.api.Domain.Vehicle.HomeResponseDTO(v, COUNT(v.placa) AS
    // quantidade_total)

    // v INNER JOIN v.modelo AS m INNER JOIN v.agencia AS a LEFT JOIN v.reserve AS r
    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment WHERE user.id = :id ORDER BY reserve.id DESC")
    List<Reserve> findByIdReserve(Long id);
}
