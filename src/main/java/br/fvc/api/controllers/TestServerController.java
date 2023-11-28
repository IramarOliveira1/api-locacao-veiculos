package br.fvc.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServerController {

    @GetMapping(value = "/ssl-test")
    public String greeting() {
        return "TESTANDO SSL!!";
    }

}
