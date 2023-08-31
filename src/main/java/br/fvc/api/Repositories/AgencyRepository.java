package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Domain.Agency.AgencyRequestDTO;
import br.fvc.api.Domain.Agency.AgencyResponseDTO;
import br.fvc.api.Models.Agency;

public interface AgencyRepository  extends JpaRepository<Agency, Long>{

    boolean existsByNome(String nome);

    boolean existsByTelefone(String telefone);

    @Query("SELECT a FROM agencia a WHERE a.nome LIKE %:nome%")
    List<Agency> findByNome(String nome);
    
}
