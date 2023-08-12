package br.fvc.api.Services;

import java.util.List;

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
}
