package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericRequestDTO;
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
        List<VehicleResponseDTO> vehicles = vehicleRepository.findAllVehicleOrderByIdDesc().stream()
                .map(VehicleResponseDTO::new).toList();
        return ResponseEntity.status(200).body(vehicles);
    }

    public ResponseEntity<Object> index(Long id) {
        List<VehicleResponseDTO> vehicles = vehicleRepository.findById(id).stream()
                .map(VehicleResponseDTO::new).toList();
        return ResponseEntity.status(200).body(vehicles);
    }

    public ResponseEntity<Object> store(VehicleRequestDTO vehicleDTO) {
        try {

            if (vehicleRepository.existsByPlaca(vehicleDTO.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa j� cadastrada"));
            }

            Vehicle vehicle = new Vehicle(vehicleDTO);

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

    public ResponseEntity<Object> update(Long id, VehicleRequestDTO vehicleDTO) {
        try {
            Vehicle vehicle = vehicleRepository.findById(id).get();

            if (!vehicleDTO.placa.equals(vehicle.getPlaca()) && vehicleRepository.existsByPlaca(vehicleDTO.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa já existe"));
            }

            vehicle.setAno(vehicleDTO.ano);
            vehicle.setCapacidade(vehicleDTO.capacidade);
            vehicle.setCategoria(vehicleDTO.categoria.toUpperCase());
            vehicle.setCor(vehicleDTO.cor.toUpperCase());
            vehicle.setMarca(vehicleDTO.marca.toUpperCase());
            vehicle.setModelo(vehicleDTO.modelo);
            vehicle.setPlaca(vehicleDTO.placa.toUpperCase());
            vehicle.setAgencia(vehicleDTO.agencia);

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
