package br.fvc.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.Example;
import br.fvc.api.Services.ExampleService;

@CrossOrigin
@RestController
@RequestMapping("/example")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping
    public List<Example> all() {
        return exampleService.findAll();
    }

}
