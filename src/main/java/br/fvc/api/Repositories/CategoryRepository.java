package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.Models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
