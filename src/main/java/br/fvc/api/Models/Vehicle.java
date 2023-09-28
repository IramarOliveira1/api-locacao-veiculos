package br.fvc.api.Models;

import br.fvc.api.Domain.Vehicle.VehicleRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@Entity(name = "veiculo")
@Table(name = "veiculo")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    private Boolean disponivel;

    @Column(nullable = false, columnDefinition = "Varchar(80)")
    private String status;

    @ManyToOne()
    @JoinColumn(name = "id_agencia", nullable = false)
    private Agency agencia;

    @ManyToOne()
    @JoinColumn(name = "id_modelo", nullable = false)
    private Model modelo;

    public Vehicle(VehicleRequestDTO data) {
        this.marca = data.marca.toUpperCase();
        this.ano = data.ano;
        this.placa = data.placa.toUpperCase();
        this.capacidade = data.capacidade;
        this.cor = data.cor.toUpperCase();
        this.status = "DISPON�VEL";
        this.agencia = data.agencia;
        this.modelo = data.modelo;
    }
}
