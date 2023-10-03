package br.fvc.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Repositories.PaymentRepository;

@Service
public class DashboardService {

    @Autowired
    private PaymentRepository paymentRepository;

    public ResponseEntity<Object> all() {
        return ResponseEntity.status(200).body(paymentRepository.getSumTotalValuesMonth());
    }
}
