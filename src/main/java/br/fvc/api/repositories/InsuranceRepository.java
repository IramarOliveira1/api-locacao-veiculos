package br.fvc.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.models.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    boolean existsByNome(String nome);

    @Query("SELECT DISTINCT i FROM seguro i ORDER BY i.id DESC")
    List<Insurance> findAllInsuranceOrderByIdDesc();

    @Query("SELECT u FROM seguro u WHERE u.nome LIKE %:nome%")
    List<Insurance> findTipoCobertura(String nome);
}
