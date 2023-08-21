package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Models.PaymentType;
import br.fvc.api.Repositories.PaymentTypeRepository;

@Service
public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> findAll() {
        return paymentTypeRepository.findAll();
    }

    public ResponseEntity<Object> createPaymentType(String tipo) {
        try {

            if (paymentTypeRepository.existsByTipo(tipo)) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Tipo de pagamento já existe!"));
            }

            PaymentType newPaymentType = new PaymentType();

            newPaymentType.setTipo(tipo);

            paymentTypeRepository.save(newPaymentType);

            return ResponseEntity.status(201)
                    .body(new GenericResponseDTO(false, "Tipo de pagamento criado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public PaymentType getPaymentTypeById(Long id) {
        return paymentTypeRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Object> updatePaymentType(Long id, PaymentType data) {
        try {
            PaymentType paymentType = paymentTypeRepository.findById(id).get();

            if (!data.getTipo().equals(paymentType.getTipo()) && paymentTypeRepository.existsByTipo(data.getTipo())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Tipo de pagamento já existe!"));
            }

            paymentType.setTipo(data.getTipo());

            paymentTypeRepository.save(paymentType);

            return ResponseEntity.status(200)
                    .body(new GenericResponseDTO(false, "Tipo de pagamento atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> deletePaymentTypeById(Long id) {
        paymentTypeRepository.deleteById(id);
        return ResponseEntity.status(200)
                .body(new GenericResponseDTO(false, "Tipo de pagamento deletado com sucesso!"));
    }
}
