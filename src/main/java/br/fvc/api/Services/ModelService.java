package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Models.Model;
import br.fvc.api.Repositories.ModelRepository;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public List<Model> findAll() {
        return modelRepository.findAll();

    }

    public ResponseEntity<Object> store(Model model) {
        try {
            if (modelRepository.existsByNome(model.getNome())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Modelo de carro já existe!"));
            }

            Model newModel = new Model();

            newModel = model;

            modelRepository.save(newModel);

            return ResponseEntity.status(201)
                    .body(new GenericResponseDTO(false, "Modelo de carro criado com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {
            modelRepository.deleteById(id);
            return ResponseEntity.status(200)
                    .body(new GenericResponseDTO(false, "Modelo de carro excluido com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }
}
