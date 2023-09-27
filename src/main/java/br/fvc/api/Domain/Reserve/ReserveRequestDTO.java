package br.fvc.api.Domain.Reserve;

import java.sql.Date;

import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Insurance;
import br.fvc.api.Models.Payment;
import br.fvc.api.Models.User;
import br.fvc.api.Models.Vehicle;

public class ReserveRequestDTO {
    public String startDateRent;
    public Date endDateRent;
    public Agency startAgency;
    public Agency endAgency;
    public Insurance insurance;
    public Payment payment;
    public User user;
    public Vehicle vehicle;
}
