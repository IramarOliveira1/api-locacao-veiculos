package br.fvc.api.dtos.reserve;

import br.fvc.api.models.Agency;
import br.fvc.api.models.Insurance;
import br.fvc.api.models.Payment;
import br.fvc.api.models.User;
import br.fvc.api.models.Vehicle;

public class ReserveRequestDTO {
    public String startDateRent;
    public String endDateRent;
    public Agency startAgency;
    public Agency endAgency;
    public Insurance insurance;
    public Payment payment;
    public User user;
    public Vehicle vehicle;
    public Long code;
}
