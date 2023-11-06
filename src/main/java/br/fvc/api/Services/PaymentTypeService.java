package br.fvc.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.dtos.generic.GenericResponseDTO;
import br.fvc.api.models.PaymentType;
import br.fvc.api.repositories.PaymentTypeRepository;

@Service
public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> findAll() {
        return paymentTypeRepository.findAll();
    }

    public ResponseEntity<Object> createPaymentType(String tipo) {
        try {

            if (paymentTypeRepository.existsByNome(tipo)) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Tipo de pagamento j� existe!"));
            }

            PaymentType newPaymentType = new PaymentType();

            newPaymentType.setNome(tipo.toUpperCase());

            paymentTypeRepository.save(newPaymentType);

            return ResponseEntity.status(201)
                    .body(new GenericResponseDTO(false, "Tipo de pagamento criado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> getPaymentTypeById(PaymentType request) {
        List<PaymentType> paymentType = paymentTypeRepository.findTypePayment(request.getNome());

        if (paymentType.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Tipo de pagamento não encontrado!"));
        }

        return ResponseEntity.status(200).body(paymentType);
    }

    public ResponseEntity<Object> updatePaymentType(Long id, PaymentType data) {
        try {
            PaymentType paymentType = paymentTypeRepository.findById(id).get();

            if (!data.getNome().equals(paymentType.getNome()) && paymentTypeRepository.existsByNome(data.getNome())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Tipo de pagamento j� existe!"));
            }

            paymentType.setNome(data.getNome().toUpperCase());

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
