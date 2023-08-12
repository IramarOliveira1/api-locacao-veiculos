package br.fvc.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.PaymentType;
import br.fvc.api.Services.PaymentTypeService;

@CrossOrigin
@RestController
@RequestMapping("/paymentTypes")
public class PaymentTypeController {    

    @Autowired
    private PaymentTypeService _paymentTypeService;

    @GetMapping
    public List<PaymentType> getAllPaymentTypes() {
        return _paymentTypeService.findAll();
    }

    @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deletePaymentType(@PathVariable Long id) {
        _paymentTypeService.deletePaymentTypeById(id);
        return ResponseEntity.ok("Tipo de pagamento deletado com sucesso!");
    }

    @PostMapping
    public ResponseEntity<String> createPaymentType(@RequestBody PaymentType paymentType) {
        PaymentType newPaymentType = _paymentTypeService.createPaymentType(paymentType.getTipo());

        if (newPaymentType == null) {
            return ResponseEntity.badRequest().body("Tipo de pagamento já existente");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Tipo de pagamento criado com sucesso");
    }
}
