package br.fvc.api.controllers;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import br.fvc.api.dtos.reserve.ReserveRequestDTO;
import br.fvc.api.services.ReserveService;
import br.fvc.api.dtos.generic.GenericRequestDTO;

@CrossOrigin
@RestController

@RequestMapping("/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @GetMapping("/all")
    public ResponseEntity<Object> all(@RequestParam("page") int page, @RequestParam("size") int size) {
        return reserveService.all(page, size);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> reserve(@RequestBody ReserveRequestDTO data) {
        return reserveService.reserve(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> index(@RequestParam("page") int page, @RequestParam("size") int size,
            @PathVariable("id") Long id) {
        return reserveService.index(page, size, id);
    }

    @PostMapping("/cancellation/{id}")
    public ResponseEntity<Object> cancellation(@PathVariable("id") Long id) {
        return reserveService.cancellation(id);
    }

    @PostMapping("/startRent/{id}")
    public ResponseEntity<Object> startRent(@PathVariable("id") Long id) {
        return reserveService.startRent(id);
    }

    @PostMapping("/endRent/{id}")
    public ResponseEntity<Object> endRent(@PathVariable("id") Long id) {
        return reserveService.endRent(id);
    }

    @PostMapping("/filter/{id}")
    public ResponseEntity<Object> filter(@RequestParam("page") int page, @RequestParam("size") int size,
            @RequestBody GenericRequestDTO data, @PathVariable("id") Long id) {
        return reserveService.filter(page, size, data, id);
    }

    @PostMapping("/filterCode/{id}")
    public ResponseEntity<Object> filterCode(@RequestParam("page") int page, @RequestParam("size") int size,
            @RequestBody ReserveRequestDTO data, @PathVariable("id") Long id) {
        return reserveService.filterCode(page, size, data, id);
    }

    @PostMapping("/filterStatusAll")
    public ResponseEntity<Object> filterStatusAll(@RequestParam("page") int page, @RequestParam("size") int size,
            @RequestBody GenericRequestDTO data) {
        return reserveService.filterStatusAll(page, size, data);
    }

    @PostMapping("/filterCodeAll")
    public ResponseEntity<Object> filterCodeAll(@RequestParam("page") int page, @RequestParam("size") int size,
            @RequestBody ReserveRequestDTO data) {
        return reserveService.filterCodeAll(page, size, data);
    }
}
