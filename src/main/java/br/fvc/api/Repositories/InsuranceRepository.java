package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.fvc.api.Models.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    boolean existsByTipo(String tipo);

    @Query("SELECT u FROM seguro u WHERE u.tipo LIKE %:tipo%")
    List<Insurance> findTipoCobertura(String tipo);
}
