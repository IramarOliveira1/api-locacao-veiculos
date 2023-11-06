package br.fvc.api.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.dtos.generic.GenericRequestDTO;
import br.fvc.api.dtos.generic.GenericResponseDTO;
import br.fvc.api.dtos.reserve.ReserveRequestDTO;
import br.fvc.api.dtos.reserve.ReserveResponseDTO;
import br.fvc.api.models.Agency;
import br.fvc.api.models.Payment;
import br.fvc.api.models.Reserve;
import br.fvc.api.models.User;
import br.fvc.api.models.Vehicle;
import br.fvc.api.repositories.AgencyRepository;
import br.fvc.api.repositories.PaymentRepository;
import br.fvc.api.repositories.ReserveRepository;
import br.fvc.api.repositories.UserRepository;

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

    public ResponseEntity<Object> all(int page, int size) {
        try {
            Pageable paginate = PageRequest.of(page, size);

            Page<Reserve> pageReserve = this.reserveRepository.findAllReserve(paginate);

            List<ReserveResponseDTO> reserves = pageReserve.stream()
                    .map(e -> new ReserveResponseDTO(e, pageReserve)).toList();

            return ResponseEntity.status(200).body(reserves);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> index(int page, int size, Long id) {
        try {
            Pageable paginate = PageRequest.of(page, size);

            Page<Reserve> pageReserve = this.reserveRepository.findByIdReserve(paginate, id);

            List<ReserveResponseDTO> reserves = pageReserve.stream()
                    .map(e -> new ReserveResponseDTO(e, pageReserve)).toList();

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

            List<Reserve> reserveExists = this.reserveRepository.findByVehicleDate(convertStartDateSql,
                    convertEndDateSql,
                    reserveRequestDTO.vehicle.getModelo().getId(), reserveRequestDTO.startAgency.getId());

            Reserve reserve = new Reserve();

            reserve.setVeiculo(reserveRequestDTO.vehicle);

            if (reserveExists.size() > 0) {
                ArrayList<Long> values = new ArrayList<>();

                for (int i = 0; i < reserveExists.size(); i++) {
                    values.add(reserveExists.get(i).getVeiculo().getId());
                }

                List<Vehicle> vehicleExists = this.reserveRepository.findByModel(
                    reserveExists.get(0).getVeiculo().getModelo().getId(), values,
                    reserveRequestDTO.startAgency.getId());

                if (vehicleExists.size() > 0) {
                    reserve.setVeiculo(vehicleExists.get(0));
                } else {
                    return ResponseEntity.status(400).body(
                            new GenericResponseDTO(false,
                                    "Desculpe, mas o item selecionado j� est� alugado. Por favor, escolha outra op��o! "));
                }
            }

            User user = userRepository.findUserId(reserveRequestDTO.user.getId());

            List<Agency> agency =
            agencyRepository.whereIn(reserveRequestDTO.startAgency.getId(),
            reserveRequestDTO.endAgency.getId());

            Payment payment = new Payment();

            payment.setData_pagamento(LocalDate.now());
            payment.setPreco(reserveRequestDTO.payment.getPreco());
            payment.setUrl_pdf("testando.pdf");
            payment.setTipo_pagamento(reserveRequestDTO.payment.getTipo_pagamento());
            paymentRepository.save(payment);

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
            Reserve reserve = reserveRepository.findByIdCodeReserve(id);

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

            Reserve reserve = reserveRepository.findByIdCodeReserve(id);

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

            Reserve reserve = reserveRepository.findByIdCodeReserve(id);

            long now = System.currentTimeMillis();

            java.sql.Date currentDate = new java.sql.Date(now);

            if (!reserve.getAgenciaDevolucao().getId().equals(reserve.getAgenciaRetirada().getId())) {
                reserve.getAgenciaRetirada()
                        .setQuantidade_total(reserve.getAgenciaRetirada().getQuantidade_total() - 1);
                reserve.getAgenciaDevolucao()
                        .setQuantidade_total(reserve.getAgenciaDevolucao().getQuantidade_total() +
                                1);

                reserve.getVeiculo().setAgencia(reserve.getAgenciaDevolucao());
            }

            if (reserve.getData_fim_aluguel().toString().equals(currentDate.toString())) {
                reserve.setStatus("FINALIZADO");
            } else if (reserve.getData_fim_aluguel().getTime() > now) {
                reserve.setStatus("ENTREGUE ANTES DO PRAZO");
            } else {
                reserve.setStatus("ENTREGUE FORA DO PRAZO");
            }

            reserve.getVeiculo().setStatus("DISPONÍVEL");

            reserve.setData_entrega(currentDate);

            reserve.getVeiculo().setDisponivel(true);

            reserveRepository.save(reserve);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Reserva finalizada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filter(int page, int size, GenericRequestDTO data, Long id) {
        try {

            Pageable paginate = PageRequest.of(page, size);

            Page<Reserve> pageReserve = this.reserveRepository.findByStatus(paginate, data.status, id);

            List<ReserveResponseDTO> reserves = pageReserve.stream()
                    .map(e -> new ReserveResponseDTO(e, pageReserve)).toList();

            return ResponseEntity.status(200).body(reserves);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filterCode(int page, int size, ReserveRequestDTO data, Long id) {
        try {

            Pageable paginate = PageRequest.of(page, size);

            Page<Reserve> pageReserve = this.reserveRepository.findByCodeReserve(paginate, data.code, id);

            List<ReserveResponseDTO> reserves = pageReserve.stream()
                    .map(e -> new ReserveResponseDTO(e, pageReserve)).toList();

            return ResponseEntity.status(200).body(reserves);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filterStatusAll(int page, int size, GenericRequestDTO data) {
        try {

            Pageable paginate = PageRequest.of(page, size);

            Page<Reserve> pageReserve = this.reserveRepository.findByAllStatus(paginate, data.status);

            List<ReserveResponseDTO> reserves = pageReserve.stream()
                    .map(e -> new ReserveResponseDTO(e, pageReserve)).toList();

            return ResponseEntity.status(200).body(reserves);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filterCodeAll(int page, int size, ReserveRequestDTO data) {
        try {

            Pageable paginate = PageRequest.of(page, size);

            Page<Reserve> pageReserve = this.reserveRepository.findByAllCodeReserve(paginate, data.code);

            List<ReserveResponseDTO> reserves = pageReserve.stream()
                    .map(e -> new ReserveResponseDTO(e, pageReserve)).toList();

            return ResponseEntity.status(200).body(reserves);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }
}
