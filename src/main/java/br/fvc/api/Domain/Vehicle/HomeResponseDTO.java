package br.fvc.api.Domain.Vehicle;

import br.fvc.api.Models.Model;
import br.fvc.api.Models.Vehicle;

public class HomeResponseDTO {
    public Long id;
    public String brand;
    public int year;
    public String place;
    public int capacity;
    public String category;
    public Long vehicle_available;
    public Model model;

    public HomeResponseDTO(Vehicle vehicle, Long vehicle_available) {
        vehicle.getModelo().setUrl_imagem("http://localhost:8080/" + vehicle.getModelo().getUrl_imagem());
        this.id = vehicle.getId();
        this.brand = vehicle.getMarca();
        this.year = vehicle.getAno();
        this.place = vehicle.getPlaca();
        this.capacity = vehicle.getCapacidade();
        this.category = vehicle.getCategoria();
        this.vehicle_available = vehicle_available;
        this.model = vehicle.getModelo();
    }
}
