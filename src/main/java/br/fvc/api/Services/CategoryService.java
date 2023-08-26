package br.fvc.api.Services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fvc.api.Models.Category;
import br.fvc.api.Repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

}
