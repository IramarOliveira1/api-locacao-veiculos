package br.fvc.api.Repositories;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Reserve;
import br.fvc.api.Models.Vehicle;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment WHERE user.id = :id ORDER BY reserve.id DESC")
    Page<Reserve> findByIdReserve(Pageable page, Long id);

    @Query("SELECT r FROM reserva r WHERE r.id = :id")
    Reserve findByIdCodeReserve(Long id);

    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment WHERE reserve.status = :status AND user.id = :id ORDER BY reserve.id DESC")
    Page<Reserve> findByStatus(Pageable page, String status, Long id);

    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment WHERE reserve.codigo_reserva = :code AND user.id = :id ORDER BY reserve.id DESC")
    Page<Reserve> findByCodeReserve(Pageable page, Long code, Long id);

    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment ORDER BY reserve.id DESC")
    Page<Reserve> findAllReserve(Pageable page);

    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment WHERE reserve.status = :status ORDER BY reserve.id DESC")
    Page<Reserve> findByAllStatus(Pageable page, String status);

    @Query("SELECT reserve FROM reserva AS reserve INNER JOIN reserve.usuario AS user INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model INNER JOIN reserve.seguro AS insurance INNER JOIN reserve.pagamento AS payment INNER JOIN payment.tipo_pagamento AS typePayment WHERE reserve.codigo_reserva = :code ORDER BY reserve.id DESC")
    Page<Reserve> findByAllCodeReserve(Pageable page, Long code);

    @Query("SELECT DISTINCT reserve FROM reserva AS reserve INNER JOIN reserve.veiculo AS vehicle INNER JOIN vehicle.modelo AS model WHERE model.id = :id_modelo AND reserve.data_inicio_aluguel BETWEEN :start AND :end AND reserve.data_fim_aluguel BETWEEN :start AND :end")
    List<Reserve> findByVehicleDate(Date start, Date end, Long id_modelo);

    @Query("SELECT vehicle FROM veiculo AS vehicle INNER JOIN vehicle.modelo AS model WHERE vehicle.id NOT IN (:id_veiculo) AND model.id = :id_modelo")
    List<Vehicle> findByModel(Long id_modelo, ArrayList<Long> id_veiculo);
}
