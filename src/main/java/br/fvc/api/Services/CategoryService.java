package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Models.Category;
import br.fvc.api.Repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public ResponseEntity<Object> store(Category category) {
        try {
            if (categoryRepository.existsByNome(category.getNome())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Categoria já existe!"));
            }

            Category newCategory = new Category();

            newCategory = category;

            categoryRepository.save(newCategory);

            return ResponseEntity.status(201)
                    .body(new GenericResponseDTO(false, "Categoria criada com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filter(Category category) {
        List<Category> categories = this.categoryRepository.findByNome(category.getNome()).stream()
                .toList();

        if (categories.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new GenericResponseDTO(true, "Nehuma categoria encontrada!"));
        }

        return ResponseEntity.status(200).body(categories);
    }

    public ResponseEntity<Object> update(Long id, Category category) {
        Category categoryUpdate = categoryRepository.findById(id).get();

        if (category.getNome().equals(categoryUpdate.getNome())) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, "Categoria já existe!"));
        }

        categoryUpdate.setNome(category.getNome());

        categoryRepository.save(categoryUpdate);

        return ResponseEntity.status(200)
                .body(new GenericResponseDTO(false, "Categoria atualizada com sucesso!"));

    }

    public ResponseEntity<Object> delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            return ResponseEntity.status(200)
                    .body(new GenericResponseDTO(false, "Categoria excluida com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}