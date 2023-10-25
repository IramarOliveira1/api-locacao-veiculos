package br.fvc.api.Domain.Reserve;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.domain.Page;

import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Insurance;
import br.fvc.api.Models.Payment;
import br.fvc.api.Models.Reserve;
import br.fvc.api.Models.Vehicle;

public class ReserveResponseDTO {
    public Long id;
    public String name;
    public String cpf;
    public String email;
    public Long codeReserve;
    public String status;
    public Date startDateRent;
    public Date endDateRent;
    public LocalDate dateRent;
    public Insurance insurance;
    public Payment payment;
    public Vehicle vehicle;
    public Agency agencyRentInit;
    public Agency agencyRendEnd;
    public Date dateDevolution;
    public Long totalElements;

    public ReserveResponseDTO(Reserve reserve, Page<Reserve> page) {
        this.id = reserve.getId();
        this.name = reserve.getUsuario().getNome();
        this.cpf = reserve.getUsuario().getCpf();
        this.email = reserve.getUsuario().getEmail();
        this.startDateRent = reserve.getData_inicio_aluguel();
        this.endDateRent = reserve.getData_fim_aluguel();
        this.dateRent = reserve.getData_reserva();
        this.status = reserve.getStatus();
        this.codeReserve = reserve.getCodigo_reserva();
        this.insurance = reserve.getSeguro();
        this.payment = reserve.getPagamento();
        this.vehicle = reserve.getVeiculo();
        this.agencyRentInit = reserve.getAgenciaRetirada();
        this.agencyRendEnd = reserve.getAgenciaDevolucao();
        this.dateDevolution = reserve.getData_entrega();
        this.totalElements = page.getTotalElements();
    }
}
