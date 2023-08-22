package br.fvc.api.Domain.Vehicle;

import java.math.BigDecimal;

import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Category;
import br.fvc.api.Models.Model;

public class VehicleRequestDTO {
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

}
