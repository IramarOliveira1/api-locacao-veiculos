package br.fvc.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.Vehicle;
import br.fvc.api.Services.VehicleService;

@CrossOrigin
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
   
    
    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> all() {
        return vehicleService.findAll();
    }

     @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);

        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veiculo não encontrado");
        }
    }
}
