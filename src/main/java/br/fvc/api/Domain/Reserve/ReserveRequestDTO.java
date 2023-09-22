package br.fvc.api.Domain.Reserve;

import java.sql.Date;

import br.fvc.api.Models.Insurance;
import br.fvc.api.Models.Payment;
import br.fvc.api.Models.User;
import br.fvc.api.Models.Vehicle;

public class ReserveRequestDTO {
    public Date startDateRent;
    public Date endDateRent;
    public Insurance insurance;
    public Payment payment;
    public User user;
    public Vehicle vehicle;
}
