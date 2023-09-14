package br.fvc.api.Domain.Vehicle;

import java.sql.Date;

import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Model;

public class VehicleRequestDTO {
    public String marca;
    public int ano;
    public String placa;
    public int capacidade;
    public String cor;
    public String categoria;
    public Agency agencia;
    public Model modelo;
    public Date startDate;
    public Date endDate;
}
