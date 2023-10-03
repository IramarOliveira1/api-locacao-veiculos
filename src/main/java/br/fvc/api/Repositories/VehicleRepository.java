package br.fvc.api.Repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Domain.Vehicle.HomeResponseDTO;
import br.fvc.api.Models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT DISTINCT v FROM veiculo v JOIN FETCH v.agencia JOIN FETCH v.modelo ORDER BY v.id DESC")
    List<Vehicle> findAllVehicleOrderByIdDesc();

    boolean existsByPlaca(String placa);

    @Query("SELECT v FROM veiculo v JOIN FETCH v.modelo AS m WHERE m.nome LIKE %:modeloOrMarcaOrPlaca% OR v.marca LIKE %:modeloOrMarcaOrPlaca% OR v.placa LIKE %:modeloOrMarcaOrPlaca%")
    List<Vehicle> findByModeloOrMarcaOrPlaca(String modeloOrMarcaOrPlaca);

    @Query("SELECT NEW br.fvc.api.Domain.Vehicle.HomeResponseDTO(v, COUNT(v.placa) AS quantidade_total) FROM veiculo v INNER JOIN v.modelo AS m INNER JOIN v.agencia AS a LEFT JOIN reserva AS r ON r.veiculo = v AND r.data_inicio_aluguel BETWEEN :start AND :end AND r.data_fim_aluguel BETWEEN :start AND :end  AND (r.status <> 'RESERVADO' OR r.status <> 'EM ANDAMENTO') WHERE r.id IS NULL AND a.id = :id_agency AND v.disponivel > FALSE GROUP BY m.nome")
    List<HomeResponseDTO> findByListVehicles(Date start, Date end, Long id_agency);
}