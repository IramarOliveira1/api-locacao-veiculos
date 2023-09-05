package br.fvc.api.Services;

import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.Vehicle.GenericRequestDTO;
import br.fvc.api.Domain.Vehicle.VehicleRequestDTO;
import br.fvc.api.Domain.Vehicle.VehicleResponseDTO;
import br.fvc.api.Models.Vehicle;
import br.fvc.api.Repositories.VehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public ResponseEntity<Object> findAll() {
        List<VehicleResponseDTO> vehicles = vehicleRepository.findAll().stream()
                .map(VehicleResponseDTO::new).toList();
        return ResponseEntity.status(200).body(vehicles);
    }

    public ResponseEntity<Object> store(String data, MultipartFile image) {
        try {
            // ObjectMapper mapper = new ObjectMapper();

            // VehicleRequestDTO vehicle = null;

            // vehicle = mapper.readValue(data, VehicleRequestDTO.class);

            this.uploadImage(image);

            // if (vehicleRepository.existsByPlaca(data.placa)) {
            // return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa j�
            // cadastrada"));
            // }

            // Vehicle vehicle = new Vehicle(data);

            // vehicleRepository.save(vehicle);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Veiculo cadastrado com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public String uploadImage(MultipartFile image) {
        try {

            File currentPath = new File("");
            String path = currentPath.getAbsolutePath();

            if (!Files.exists(Paths.get(path + "\\src\\main\\resources\\static\\public\\image"))) {
                Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\static\\public\\image"));
            }

            Timestamp name_file = new Timestamp(System.currentTimeMillis());

            String extension = com.google.common.io.Files.getFileExtension(image.getOriginalFilename());

            Path url = Files.write(
                    Paths.get(path + "\\src\\main\\resources\\static\\public\\image\\" + name_file.getTime() + "."
                            + extension),
                    image.getBytes());

            System.out.println(url);

            return "";
        } catch (Exception e) {
            return "";
            // TODO: handle exception
        }
    }

    public ResponseEntity<Object> filter(GenericRequestDTO data) {
        List<VehicleResponseDTO> vehicle = this.vehicleRepository.findByModeloOrMarca(data.modelOrMarca).stream()
                .map(VehicleResponseDTO::new).toList();

        if (vehicle.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Veiculo não encontrado!"));
        }

        return ResponseEntity.status(200).body(vehicle);
    }

    public ResponseEntity<Object> update(Long id, VehicleRequestDTO data) {
        try {
            Vehicle vehicle = vehicleRepository.findById(id).get();

            if (!data.placa.equals(vehicle.getPlaca()) && vehicleRepository.existsByPlaca(data.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa já existe"));
            }

            vehicle.setAno(data.ano);
            vehicle.setCapacidade(data.capacidade);
            vehicle.setCategoria(data.categoria);
            vehicle.setCor(data.cor);
            vehicle.setMarca(data.marca);
            vehicle.setModelo(data.modelo);
            vehicle.setPlaca(data.placa);
            vehicle.setValor_diaria(data.valor_diaria);
            vehicle.setUrl_imagem(data.url_imagem);
            vehicle.setAgencia(data.agencia);

            vehicleRepository.save(vehicle);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Veiculo atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {
            vehicleRepository.deleteById(id);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Veiculo excluido com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}
