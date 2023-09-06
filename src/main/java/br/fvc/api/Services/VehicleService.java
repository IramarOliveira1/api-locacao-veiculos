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
            ObjectMapper mapper = new ObjectMapper();

            VehicleRequestDTO vehicleDTO = null;

            vehicleDTO = mapper.readValue(data, VehicleRequestDTO.class);

            if (vehicleRepository.existsByPlaca(vehicleDTO.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa j� cadastrada"));
            }

            String removeComma = vehicleDTO.valor_diaria.replace(",", "");
            String removePoint = removeComma.replace(".", "");

            StringBuilder addPoint = new StringBuilder(removePoint);
            addPoint.insert(removePoint.length() - 2, '.');

            String url = this.uploadImage(image);

            Vehicle vehicle = new Vehicle(vehicleDTO, addPoint.toString(), url);

            vehicleRepository.save(vehicle);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Veiculo cadastrado com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
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

    public ResponseEntity<Object> update(Long id, String data, MultipartFile image) {
        try {
            Vehicle vehicle = vehicleRepository.findById(id).get();

            ObjectMapper mapper = new ObjectMapper();

            VehicleRequestDTO vehicleDTO = null;

            vehicleDTO = mapper.readValue(data, VehicleRequestDTO.class);

            if (!vehicleDTO.placa.equals(vehicle.getPlaca()) && vehicleRepository.existsByPlaca(vehicleDTO.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa já existe"));
            }

            String removeComma = vehicleDTO.valor_diaria.replace(",", "");
            String removePoint = removeComma.replace(".", "");

            StringBuilder addPoint = new StringBuilder(removePoint);
            addPoint.insert(removePoint.length() - 2, '.');

            this.deleteImage(id);

            String url = this.uploadImage(image);

            vehicle.setAno(vehicleDTO.ano);
            vehicle.setCapacidade(vehicleDTO.capacidade);
            vehicle.setCategoria(vehicleDTO.categoria);
            vehicle.setCor(vehicleDTO.cor);
            vehicle.setMarca(vehicleDTO.marca);
            vehicle.setModelo(vehicleDTO.modelo);
            vehicle.setPlaca(vehicleDTO.placa);
            vehicle.setValor_diaria(addPoint.toString());
            vehicle.setUrl_imagem(url);
            vehicle.setAgencia(vehicleDTO.agencia);

            vehicleRepository.save(vehicle);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Veiculo atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {

            this.deleteImage(id);

            vehicleRepository.deleteById(id);
            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Veiculo excluido com sucesso"));
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
            Vehicle vehicle = vehicleRepository.findById(id).get();

            File currentPath = new File("");
            String path = currentPath.getAbsolutePath();

            if (Files.exists(Paths.get(path + "\\src\\main\\resources\\static\\" + vehicle.getUrl_imagem()))) {

                Files.delete(Paths.get(path + "\\src\\main\\resources\\static\\" + vehicle.getUrl_imagem()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
