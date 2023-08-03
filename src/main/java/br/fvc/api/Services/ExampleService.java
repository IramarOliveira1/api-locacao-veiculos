package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fvc.api.Models.Example;
import br.fvc.api.Repositories.ExampleRepository;

@Service
public class ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;

    public List<Example> findAll() {
        return exampleRepository.findAll();
    }

}
