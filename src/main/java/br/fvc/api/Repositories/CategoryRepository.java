package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fvc.api.Models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNome(String nome);


       @Query("SELECT c FROM categoria c WHERE c.nome LIKE %:name%")
    List<Category> findByNome(String name);
    
}
