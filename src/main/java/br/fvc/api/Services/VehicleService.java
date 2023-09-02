package br.fvc.api.Services;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
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

    public ResponseEntity<Object> store(VehicleRequestDTO data) {
        try {
            if (vehicleRepository.existsByPlaca(data.placa)) {
                return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Placa já cadastrada"));
            }

            Vehicle vehicle = new Vehicle(data);

            vehicleRepository.save(vehicle);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Veiculo criado!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {
            vehicleRepository.deleteById(id);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Veiculo deletado"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}
