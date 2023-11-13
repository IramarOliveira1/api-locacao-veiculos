package br.fvc.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.dtos.generic.GenericRequestDTO;
import br.fvc.api.services.DashboardService;

@CrossOrigin
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/all")
    public ResponseEntity<Object> all() {
        return dashboardService.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> index(@PathVariable("id") Long id) {
        return dashboardService.index(id);
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filter(@RequestBody GenericRequestDTO data) {
        return dashboardService.filter(data);
    }
}
