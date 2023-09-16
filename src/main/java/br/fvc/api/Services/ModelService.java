package br.fvc.api.Services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.Model.ModelResponseDTO;
import br.fvc.api.Models.Model;
import br.fvc.api.Repositories.ModelRepository;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public ResponseEntity<Object> findAll() {
        List<ModelResponseDTO> models = modelRepository.findAllModelOrderByIdDesc().stream()
                .map(ModelResponseDTO::new).toList();
        return ResponseEntity.status(200).body(models);

    }

    public ResponseEntity<Object> store(String data, MultipartFile image) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            Model model = new Model();

            model = mapper.readValue(data, model.getClass());

            if (modelRepository.existsByNome(model.getNome())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Nome já cadastrado!"));
            }

            String removeComma = model.getValor_diaria().replace(",", "");
            String removePoint = removeComma.replace(".", "");

            StringBuilder addPoint = new StringBuilder(removePoint);
            addPoint.insert(removePoint.length() - 2, '.');

            String url = this.uploadImage(image);

            model.setNome(model.getNome().toUpperCase());
            model.setCategoria(model.getCategoria().toUpperCase());
            model.setValor_diaria(addPoint.toString());
            model.setUrl_imagem(url);

            modelRepository.save(model);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Modelo cadastrado com sucesso!"));
        } catch (

        Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> index(Long id) {
        List<ModelResponseDTO> model = modelRepository.findById(id).stream()
                .map(ModelResponseDTO::new).toList();
        return ResponseEntity.status(200).body(model);

    }

    public ResponseEntity<Object> filter(Model data) {

        System.out.println(data.getNome());
        List<Model> models = modelRepository.findByName(data.getNome());

        if (models.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true,
                    "Nenhum modelo encontrado!"));
        }

        return ResponseEntity.status(200).body(models);
    }

    public ResponseEntity<Object> update(Long id, String data, MultipartFile image) {
        try {

            Model model = modelRepository.findById(id).get();

            Model models = new Model();

            ObjectMapper mapper = new ObjectMapper();

            models = mapper.readValue(data, models.getClass());

            if (!models.getNome().equals(model.getNome()) && modelRepository.existsByNome(models.getNome())) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Nome já cadastrado!"));
            }

            String removeComma = models.getValor_diaria().replace(",", "");
            String removePoint = removeComma.replace(".", "");

            StringBuilder addPoint = new StringBuilder(removePoint);
            addPoint.insert(removePoint.length() - 2, '.');

            this.deleteImage(id);

            String url = this.uploadImage(image);

            model.setNome(models.getNome());
            model.setCategoria(models.getCategoria().toUpperCase());
            model.setValor_diaria(addPoint.toString());
            model.setUrl_imagem(url);

            modelRepository.save(model);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Modelo atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {

            this.deleteImage(id);

            modelRepository.deleteById(id);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Modelo excluido com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public String uploadImage(MultipartFile image) {
        try {
            File currentPath = new File("");
            String path = currentPath.getAbsolutePath();

            if (!Files.exists(Paths.get(path +
                    "\\src\\main\\resources\\static\\public\\image"))) {
                Files.createDirectories(Paths.get(path +
                        "\\src\\main\\resources\\static\\public\\image"));
            }

            Timestamp name_file = new Timestamp(System.currentTimeMillis());

            String extension = com.google.common.io.Files.getFileExtension(image.getOriginalFilename());

            Files.write(
                    Paths.get(path + "\\src\\main\\resources\\static\\public\\image\\" +
                            name_file.getTime() + "."
                            + extension),
                    image.getBytes());

            return "public/image/" + name_file.getTime() + "." + extension;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void deleteImage(Long id) {
        try {
            Model model = modelRepository.findById(id).get();

            File currentPath = new File("");
            String path = currentPath.getAbsolutePath();

            if (Files.exists(Paths.get(path + "\\src\\main\\resources\\static\\" + model.getUrl_imagem()))) {

                Files.delete(Paths.get(path + "\\src\\main\\resources\\static\\" + model.getUrl_imagem()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
