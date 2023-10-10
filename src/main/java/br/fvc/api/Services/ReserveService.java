package br.fvc.api.Services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.Reserve.ReserveRequestDTO;
import br.fvc.api.Domain.Reserve.ReserveResponseDTO;
import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Payment;
import br.fvc.api.Models.Reserve;
import br.fvc.api.Models.User;
import br.fvc.api.Repositories.AgencyRepository;
import br.fvc.api.Repositories.PaymentRepository;
import br.fvc.api.Repositories.ReserveRepository;
import br.fvc.api.Repositories.UserRepository;

@Service
public class ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    public ResponseEntity<Object> index(Long id) {
        try {

            List<ReserveResponseDTO> reserves = this.reserveRepository.findByIdReserve(id).stream()
                    .map(ReserveResponseDTO::new).toList();

            return ResponseEntity.status(200).body(reserves);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> reserve(ReserveRequestDTO reserveRequestDTO) {
        try {

            SimpleDateFormat convertDate = new SimpleDateFormat("yyyy-MM-dd");
            Date startDateConvert = convertDate.parse(reserveRequestDTO.startDateRent);
            Date endDateConvert = convertDate.parse(reserveRequestDTO.endDateRent);
            java.sql.Date convertStartDateSql = new java.sql.Date(startDateConvert.getTime());
            java.sql.Date convertEndDateSql = new java.sql.Date(endDateConvert.getTime());

            SimpleDateFormat convertDatePTBR = new SimpleDateFormat("dd-MM-yyyy");

            User user = userRepository.findUserId(reserveRequestDTO.user.getId());

            List<Agency> agency = agencyRepository.whereIn(reserveRequestDTO.startAgency.getId(),
                    reserveRequestDTO.endAgency.getId());

            Payment payment = new Payment();

            payment.setData_pagamento(LocalDate.now());
            payment.setPreco(reserveRequestDTO.payment.getPreco());
            payment.setUrl_pdf("testando.pdf");
            payment.setTipo_pagamento(reserveRequestDTO.payment.getTipo_pagamento());
            paymentRepository.save(payment);

            Reserve reserve = new Reserve();

            Long code = new Date().getTime();

            reserve.setData_inicio_aluguel(convertStartDateSql);
            reserve.setData_fim_aluguel(convertEndDateSql);
            reserve.setData_reserva(LocalDate.now());
            reserve.setStatus("RESERVADO");
            reserve.setCodigo_reserva(code);
            reserve.setAgenciaRetirada(reserveRequestDTO.startAgency);
            reserve.setAgenciaDevolucao(reserveRequestDTO.endAgency);
            reserve.setSeguro(reserveRequestDTO.insurance);
            reserve.setPagamento(payment);
            reserve.setUsuario(reserveRequestDTO.user);
            reserve.setVeiculo(reserveRequestDTO.vehicle);

            reserveRepository.save(reserve);

            String startAgency = null;
            String endAgency = null;

            for (int i = 0; i < agency.size(); i++) {

                if (i > 0) {
                    endAgency = agency.get(i).getAddress().getLogradouro() + " - "
                            + agency.get(i).getAddress().getBairro();
                } else {
                    startAgency = agency.get(i).getAddress().getLogradouro() + " - "
                            + agency.get(i).getAddress().getBairro();
                    endAgency = agency.get(i).getAddress().getLogradouro() + " - "
                            + agency.get(i).getAddress().getBairro();
                }
            }

            String message = "Olá " + user.getNome() + "\n código para consultar sua reserva - "
                    + code + "\n"
                    + " veículo vai estar disponivel na data ( "
                    + convertDatePTBR.format(convertStartDateSql) + " )\n"
                    + " data da devolução do veiculo ( "
                    + convertDatePTBR.format(convertEndDateSql) + " )\n"
                    + " local para retirada do veículo [" + startAgency + "]\n"
                    + " local para devoluçãoo do veiculo [" + endAgency + "]\n";

            sendMailService.sendMail(user.getEmail(), "Reserva concluida!",
                    message);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Reserva feita com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> cancellation(Long id) {
        try {
            Reserve reserve = reserveRepository.findByCodeReserve(id);

            reserve.setStatus("CANCELADO");

            reserveRepository.save(reserve);

            String message = "Ol� " + reserve.getUsuario().getNome() + "\n"
                    + "Passando para avisar que sua reserva do codigo:  " + reserve.getCodigo_reserva() + "\n"
                    + "foi cancelada com sucesso! ";

            sendMailService.sendMail(reserve.getUsuario().getEmail(), "Reserva cancelada!",
                    message);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Reserva cancelada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> startRent(Long id) {
        try {

            Reserve reserve = reserveRepository.findByCodeReserve(id);

            reserve.setStatus("EM ANDAMENTO");

            reserve.getVeiculo().setStatus("ALUGADO");

            reserve.getVeiculo().setDisponivel(false);

            reserveRepository.save(reserve);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Aluguel iniciado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> endRent(Long id) {
        try {

            Reserve reserve = reserveRepository.findByCodeReserve(id);

            if (!reserve.getAgenciaDevolucao().getId().equals(reserve.getAgenciaRetirada().getId())) {
                reserve.getAgenciaRetirada()
                        .setQuantidade_total(reserve.getAgenciaRetirada().getQuantidade_total() - 1);
                reserve.getAgenciaDevolucao()
                        .setQuantidade_total(reserve.getAgenciaDevolucao().getQuantidade_total() + 1);

                reserve.getVeiculo().setAgencia(reserve.getAgenciaDevolucao());
            }

            reserve.setStatus("FINALIZADO");

            reserve.getVeiculo().setStatus("DISPON�VEL");

            reserve.getVeiculo().setDisponivel(true);

            reserveRepository.save(reserve);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Reserva finalizada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }
}
