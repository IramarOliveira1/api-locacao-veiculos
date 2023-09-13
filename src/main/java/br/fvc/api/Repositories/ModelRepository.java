package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
    boolean existsByNome(String name);

    @Query("SELECT DISTINCT m FROM modelo m ORDER BY m.id DESC")
    List<Model> findAllModelOrderByIdDesc();

    @Query("SELECT m FROM modelo m WHERE m.nome LIKE %:name%")
    List<Model> findByName(String name);
}
