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

import br.fvc.api.Models.Model;
import br.fvc.api.Services.ModelService;

@CrossOrigin
@RestController

@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping("/all")
    public ResponseEntity<Object> all() {
        return modelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> index(@PathVariable("id") Long id) {
        return modelService.index(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestPart("name") String name,
            @RequestPart("image") MultipartFile image) {
        return modelService.store(name, image);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
            @RequestPart("name") String name,
            @RequestPart("image") MultipartFile image) {
        return modelService.update(id, name, image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return modelService.delete(id);
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filter(@RequestBody() Model data) {
        return modelService.filter(data);
    }
}
