package br.fvc.api.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.Reserve.ReserveRequestDTO;
import br.fvc.api.Domain.Reserve.ReserveResponseDTO;
import br.fvc.api.Models.Payment;
import br.fvc.api.Models.Reserve;
import br.fvc.api.Models.Vehicle;
import br.fvc.api.Repositories.PaymentRepository;
import br.fvc.api.Repositories.ReserveRepository;
import br.fvc.api.Repositories.VehicleRepository;

@Service
public class ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public ResponseEntity<Object> index(Long id) {

        List<ReserveResponseDTO> reserves = reserveRepository.findByIdReserve(id).stream()
                .map(ReserveResponseDTO::new).toList();

        return ResponseEntity.status(200).body(reserves);
    }

    public ResponseEntity<Object> reserve(ReserveRequestDTO reserveRequestDTO) {
        try {

            Payment payment = new Payment();

            payment.setData_pagamento(LocalDate.now());
            payment.setPreco(reserveRequestDTO.payment.getPreco());
            payment.setStatus("CONCLUIDO");
            payment.setUrl_pdf("testando.pdf");
            payment.setTipo_pagamento(reserveRequestDTO.payment.getTipo_pagamento());
            paymentRepository.save(payment);

            Reserve reserve = new Reserve();

            reserve.setData_inicio_aluguel(reserveRequestDTO.startDateRent);
            reserve.setData_fim_aluguel(reserveRequestDTO.endDateRent);
            reserve.setData_reserva(LocalDate.now());
            reserve.setStatus("RESERVADO");
            reserve.setSeguro(reserveRequestDTO.insurance);
            reserve.setPagamento(payment);
            reserve.setUsuario(reserveRequestDTO.user);
            reserve.setVeiculo(reserveRequestDTO.vehicle);

            reserveRepository.save(reserve);

            Vehicle vehicle = vehicleRepository.findById(reserveRequestDTO.vehicle.getId()).get();

            vehicle.setDisponivel(false);

            vehicleRepository.save(vehicle);

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Reserva feita com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }
}
