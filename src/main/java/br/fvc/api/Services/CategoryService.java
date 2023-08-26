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
                        .body(new GenericResponseDTO(true, "Categoria já existe"));
            }

            Category newCategory = new Category();

            newCategory = category;

            categoryRepository.save(newCategory);

            return ResponseEntity.status(201)
                    .body(new GenericResponseDTO(false, "Categoria criada com sucesso"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
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
