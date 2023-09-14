package br.fvc.api.Repositories;

import java.util.Date;
import java.util.List;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT DISTINCT v FROM veiculo v JOIN FETCH v.agencia JOIN FETCH v.modelo ORDER BY v.id DESC")
    List<Vehicle> findAllVehicleOrderByIdDesc();

    boolean existsByPlaca(String placa);

    @Query("SELECT v FROM veiculo v JOIN FETCH v.modelo AS m WHERE m.nome LIKE %:modeloOrMarca% or v.marca LIKE %:modeloOrMarca%")
    List<Vehicle> findByModeloOrMarca(String modeloOrMarca);

    // LEFT JOIN reserva ON veiculo.id = reserva.id_veiculo
    // AND reserva.data_inicio_aluguel <= '2023-09-15'
    // AND reserva.data_fim_aluguel >= '2023-09-13'

    // WHERE (u.nome LIKE %:nameOrCpf% OR u.cpf LIKE %:nameOrCpf%) AND u.role =
    // :role
    // r AND r.data_inicio_aluguel <= '2023-09-15' AND r.data_fim_aluguel >=
    // '2023-09-13'
    // ON r.data_fim_aluguel >= '2023-09-13
    // v INNER JOIN v.agencia INNER JOIN v.modelo AS m#WHERE reserva.id IS NULL 
// #AND agencia.id = 24
    @Query("SELECT  m.nome, COUNT(v.placa) AS veiculos_disponivel, m.quantidade FROM veiculo v INNER JOIN v.modelo AS m INNER JOIN v.agencia AS a LEFT JOIN v.reserve AS r ON r.data_inicio_aluguel <= :date AND r.data_fim_aluguel >= :date WHERE r.id IS NULL AND a.id = 24 GROUP BY m.nome ")
    List<Vehicle[]> findByListVehicles(Date date);
}
