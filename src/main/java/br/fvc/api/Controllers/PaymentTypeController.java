package br.fvc.api.Controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.PaymentType;
import br.fvc.api.Services.PaymentTypeService;

@CrossOrigin
@RestController
@RequestMapping("/payment-types")
public class PaymentTypeController {

    @Autowired
    private PaymentTypeService _paymentTypeService;

    @GetMapping("/all")
    public List<PaymentType> getAllPaymentTypes() {
        return _paymentTypeService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createPaymentType(@RequestBody PaymentType paymentType) {
        return _paymentTypeService.createPaymentType(paymentType.getNome());
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> getPaymentTypeById(@RequestBody PaymentType request) {
        return _paymentTypeService.getPaymentTypeById(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePaymentType(@PathVariable Long id,
            @RequestBody PaymentType updatedPaymentType) {
        return _paymentTypeService.updatePaymentType(id, updatedPaymentType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaymentType(@PathVariable Long id) {
        return _paymentTypeService.deletePaymentTypeById(id);
    }
}
