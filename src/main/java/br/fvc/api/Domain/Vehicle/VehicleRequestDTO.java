package br.fvc.api.Domain.Vehicle;

import java.math.BigDecimal;

import br.fvc.api.Models.Agency;

public class VehicleRequestDTO {
    public String marca;
    public  int ano;
    public  String placa;
    public int capacidade;
    public String cor;
    public String categoria;
    public String modelo;
    public BigDecimal valor_diaria;
    public String url_imagem;
    public Agency agencia;
}
