package br.fvc.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.Model;
import br.fvc.api.Services.ModelService;;

@CrossOrigin
@RestController
@RequestMapping("/model")
public class ModelController {
    
    @Autowired
    private ModelService modelService;

    @GetMapping("/all")
    public List<Model> all(){
        return modelService.findAll();
    } 

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Model model){
        return modelService.store(model);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Object> delete(@PathVariable("id") Long id ){
            return modelService.delete(id);
        }
    
}
