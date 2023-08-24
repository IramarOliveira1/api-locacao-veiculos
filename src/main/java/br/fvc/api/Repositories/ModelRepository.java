package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {

    boolean existsByNome(String nome);

    @Query("SELECT m FROM modelo m WHERE m.nome LIKE %:name%")
    List<Model> findByNome(String name);
}
