package br.fvc.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.Agency;
import br.fvc.api.Services.AgencyService;

@CrossOrigin
@RestController

@RequestMapping("/agency")
public class AgencyController {
    
    @Autowired
    private AgencyService agencyService;

    @GetMapping("/all")
    public List<Agency> findAll(){
        return agencyService.findAll();
    }

    
}
