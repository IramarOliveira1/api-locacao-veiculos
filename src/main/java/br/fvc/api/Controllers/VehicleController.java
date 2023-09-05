package br.fvc.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.fvc.api.Domain.Vehicle.GenericRequestDTO;
import br.fvc.api.Domain.Vehicle.VehicleRequestDTO;
import br.fvc.api.Services.VehicleService;

@CrossOrigin
@RestController

@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/all")
    public ResponseEntity<Object> all() {
        return vehicleService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestPart("vehicle") String data,
            @RequestPart("image") MultipartFile image) {
        return vehicleService.store(data, image);
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filter(@RequestBody GenericRequestDTO data) {
        return vehicleService.filter(data);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody VehicleRequestDTO data) {
        return vehicleService.update(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return vehicleService.delete(id);
    }
}
