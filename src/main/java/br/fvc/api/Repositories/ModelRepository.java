package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fvc.api.Models.Model;;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    boolean existsByNome(String nome);

    
}
