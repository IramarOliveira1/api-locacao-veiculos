package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Vehicle.VehicleResponseDTO;
import br.fvc.api.Repositories.VehicleRepository;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public ResponseEntity<Object> findAll(){
        List<VehicleResponseDTO> vehicles = this.vehicleRepository.findAll().stream().map(VehicleResponseDTO::new).toList();
        return ResponseEntity.status(200).body(vehicles);
    }
}
