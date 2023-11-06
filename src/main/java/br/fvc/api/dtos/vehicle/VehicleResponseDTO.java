package br.fvc.api.dtos.vehicle;

import br.fvc.api.models.Agency;
import br.fvc.api.models.Model;
import br.fvc.api.models.Vehicle;

public class VehicleResponseDTO {
    public Long id;
    public String marca;
    public int ano;
    public String placa;
    public int capacidade;
    public String cor;
    public String status;
    public Agency agencia;
    public Model modelo;

    public VehicleResponseDTO(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.marca = vehicle.getMarca();
        this.ano = vehicle.getAno();
        this.placa = vehicle.getPlaca();
        this.capacidade = vehicle.getCapacidade();
        this.cor = vehicle.getCor();
        this.agencia = vehicle.getAgencia();
        this.modelo = vehicle.getModelo();
        this.status = vehicle.getStatus();
    }
}
