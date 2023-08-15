package br.fvc.api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fvc.api.Models.Payment;
import br.fvc.api.Models.PaymentType;
import br.fvc.api.Services.PaymentService;

@CrossOrigin
@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Autowired
    PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayment() {
        return paymentService.findAll();
    }

       @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deletePaymentType(@PathVariable Long id) {
        paymentService.deletePaymentById(id);
        return ResponseEntity.ok("Tipo de pagamento deletado com sucesso!");
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);

        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de pagamento não encontrado");
        }
    }
}
