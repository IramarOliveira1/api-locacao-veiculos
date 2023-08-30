package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fvc.api.Models.Agency;
import br.fvc.api.Repositories.AgencyRepository;


@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    public List<Agency> findAll(){
        return agencyRepository.findAll();
    }
    
}
