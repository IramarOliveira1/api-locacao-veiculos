package br.fvc.api.Services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericRequestDTO;
import br.fvc.api.Repositories.PaymentRepository;

@Service
public class DashboardService {

    @Autowired
    private PaymentRepository paymentRepository;

    public ResponseEntity<Object> all() {
        return ResponseEntity.status(200).body(paymentRepository.getSumTotalValuesMonth());
    }

    public ResponseEntity<Object> index(Long id) {
        return ResponseEntity.status(200).body(paymentRepository.reserveValueTotalMonth(id));
    }

    public ResponseEntity<Object> filter(GenericRequestDTO data) {
        try {

            SimpleDateFormat convertDate = new SimpleDateFormat("dd-MM-yyyy");
            Date startDateConvert = convertDate.parse(data.start);
            Date endtDateConvert = convertDate.parse(data.end);
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDateOne = startDateConvert.toInstant().atZone(defaultZoneId).toLocalDate();
            LocalDate localDateTwo = endtDateConvert.toInstant().atZone(defaultZoneId).toLocalDate();

            return ResponseEntity.status(200).body(paymentRepository.filterInvoicingPeriod(localDateOne, localDateTwo));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
