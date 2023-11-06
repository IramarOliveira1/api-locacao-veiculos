package br.fvc.api.dtos.vehicle;

import br.fvc.api.models.Agency;
import br.fvc.api.models.Model;

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
