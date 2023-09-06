package br.fvc.api.Domain.Vehicle;

import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Vehicle;

public class VehicleResponseDTO {
    public Long id;
    public String marca;
    public int ano;
    public String placa;
    public int capacidade;
    public String cor;
    public String categoria;
    public String modelo;
    public String valor_diaria;
    public String url_imagem;
    public int quantidade;
    public Agency agencia;

    public VehicleResponseDTO(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.marca = vehicle.getMarca();
        this.ano = vehicle.getAno();
        this.placa = vehicle.getPlaca();
        this.capacidade = vehicle.getCapacidade();
        this.cor = vehicle.getCor();
        this.categoria = vehicle.getCategoria();
        this.modelo = vehicle.getModelo();
        this.quantidade = vehicle.getQuantidade();
        this.url_imagem = "http://localhost:8080/" + vehicle.getUrl_imagem();
        this.agencia = vehicle.getAgencia();
    }

}
