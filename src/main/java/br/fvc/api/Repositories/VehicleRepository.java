package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByPlaca(String placa);

    @Query("SELECT v FROM veiculo v WHERE v.modelo LIKE %:modeloOrMarca% or v.marca LIKE %:modeloOrMarca%")
    List<Vehicle> findByModeloOrMarca(String modeloOrMarca);
}
