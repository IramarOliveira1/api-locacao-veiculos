package br.fvc.api.Domain.Reserve;

import java.sql.Date;
import java.time.LocalDate;

import br.fvc.api.Models.Insurance;
import br.fvc.api.Models.Payment;
import br.fvc.api.Models.Reserve;
import br.fvc.api.Models.User;
import br.fvc.api.Models.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveResponseDTO {
    private Date startDateRent;
    private Date endDateRent;
    private LocalDate dateRent;
    private Insurance insurance;
    private Payment payment;
    private String nome;
    private String cpf;
    private Vehicle vehicle;

    public ReserveResponseDTO(Reserve reserve, User user) {
        // System.out.println(reserve.getData_inicio_aluguel());
        // this.startDateRent = reserve.getData_inicio_aluguel();
        // this.endDateRent = reserve.getData_fim_aluguel();
        // this.dateRent = reserve.getData_reserva();
        // System.out.println(user.getNome());
        this.nome = user.getNome();
        this.cpf = user.getCpf();
        // this.payment = reserve.getPagamento();
        // this.user = reserve.getUsuario();
        // this.vehicle = reserve.getVeiculo();
        // this.capacidade = vehicle.getCapacidade();
        // this.cor = vehicle.getCor();
        // this.agencia = vehicle.getAgencia();
        // this.modelo = vehicle.getModelo();
    }
}
