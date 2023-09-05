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
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Domain.Agency.AgencyRequestDTO;
import br.fvc.api.Services.AgencyService;

@CrossOrigin
@RestController

@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @GetMapping("/all")
    public  ResponseEntity<Object> findAll() {
        return agencyService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AgencyRequestDTO data) {
        return agencyService.store(data);
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filter(@RequestBody() AgencyRequestDTO data) {
        return agencyService.filter(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody AgencyRequestDTO data) {
        return agencyService.update(id, data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> index(@PathVariable("id") Long id) {
        return agencyService.index(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return agencyService.delete(id);
    }

}
