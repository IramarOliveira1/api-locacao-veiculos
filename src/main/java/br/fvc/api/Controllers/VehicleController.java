package br.fvc.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Domain.Vehicle.VehicleRequestDTO;
import br.fvc.api.Models.Vehicle;
import br.fvc.api.Repositories.VehicleRepository;
import br.fvc.api.Services.VehicleService;

@CrossOrigin
@RestController

@RequestMapping("/vehicle")
public class VehicleController {
    
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/all")
    public ResponseEntity<Object> all(){
        return vehicleService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody VehicleRequestDTO data){
        return vehicleService.store(data);
    }
    
}
