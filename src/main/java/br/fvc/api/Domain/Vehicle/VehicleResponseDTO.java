package br.fvc.api.Domain.Vehicle;

import java.math.BigDecimal;

import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Category;
import br.fvc.api.Models.Model;
import br.fvc.api.Models.Vehicle;

public class VehicleResponseDTO {
    public String brand;
    public int year;
    public String plate;
    public int capacity;
    public boolean availability;
    public BigDecimal daily_rate;
    public String url_image;
    public Agency agency;
    public Category category;
    public Model model;

    public VehicleResponseDTO(Vehicle vehicle){
        this.brand = vehicle.getMarca();
        this.year = vehicle.getAno();
        this.plate = vehicle.getPlaca();
        this.capacity = vehicle.getCapacidade();
        this.availability =  vehicle.isDisponibilidade();
        this.daily_rate = vehicle.getValor_diaria();
        this.url_image = vehicle.getUrl_imagem();
        this.agency = vehicle.getAgencia();
        this.category = vehicle.getCategoria();
        this.model = vehicle.getModel();
    }

}
