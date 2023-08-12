package br.fvc.api.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.fvc.api.Models.PaymentType;
import br.fvc.api.Repositories.PaymentTypeRepository;

@Service
public class PaymentTypeService {
    
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> findAll() {
        return paymentTypeRepository.findAll();
    }

    public void deletePaymentTypeById(Long id) {
        paymentTypeRepository.deleteById(id);
    }

    public PaymentType createPaymentType(String tipo) {
        Optional<PaymentType> existingType = paymentTypeRepository.findByTipo(tipo);

        if (existingType.isPresent()) {
            return null;
        }

        PaymentType newPaymentType = new PaymentType();
        newPaymentType.setTipo(tipo);
        return paymentTypeRepository.save(newPaymentType);
    }

    public boolean updatePaymentType(Long id, PaymentType updatedPaymentType) {
        Optional<PaymentType> existingType = paymentTypeRepository.findById(id);

        if (existingType.isPresent()) {
            PaymentType typeToUpdate = existingType.get();
            typeToUpdate.setTipo(updatedPaymentType.getTipo());
            paymentTypeRepository.save(typeToUpdate);
            return true;
        } else {
            return false;
        }
    }
}
