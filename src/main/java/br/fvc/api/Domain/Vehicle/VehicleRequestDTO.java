package br.fvc.api.Domain.Vehicle;

import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Model;

public class VehicleRequestDTO {
    public String marca;
    public int ano;
    public String placa;
    public int capacidade;
    public String cor;
    public Agency agencia;
    public Model modelo;
    public String startDate;
    public String endDate;
}
