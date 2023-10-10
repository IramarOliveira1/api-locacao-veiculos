package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Long> {

    @Query("SELECT DISTINCT a FROM agencia a ORDER BY a.id DESC")
    List<Agency> findAllAgencyOrderByIdDesc();

    boolean existsByNome(String nome);

    boolean existsByTelefone(String telefone);

    @Query("SELECT a FROM agencia a WHERE a.nome LIKE %:nome%")
    List<Agency> findByNome(String nome);

    @Query("SELECT a FROM agencia a WHERE a.id IN (:idStartAgency, :idEndAgency)")
    List<Agency> whereIn(Long idStartAgency, Long idEndAgency);
}
