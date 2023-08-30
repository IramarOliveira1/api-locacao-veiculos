package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.Models.Agency;

public interface AgencyRepository  extends JpaRepository<Agency, Long>{
    
}
