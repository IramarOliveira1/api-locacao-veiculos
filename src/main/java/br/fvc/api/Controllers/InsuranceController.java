package br.fvc.api.Controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.Insurance;
import br.fvc.api.Services.InsuranceService;

@CrossOrigin
@RestController

@RequestMapping("/insurancies")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    @GetMapping("/all")
    public List<Insurance> all() {
        return insuranceService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return insuranceService.delete(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Insurance data) {
        return insuranceService.store(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Insurance data) {
        return insuranceService.update(id, data);
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filter(@RequestBody() Insurance data) {
        return insuranceService.filter(data);
    }

}
