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
    private int quantidade;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false, columnDefinition = "Decimal(10,2)")
    private String valor_diaria;

    @Column(nullable = false)
    private String url_imagem;

    @ManyToOne
    @JoinColumn(name = "id_agencia", nullable = false)
    private Agency agencia;

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Model modelo;

    public Vehicle(VehicleRequestDTO data, String formatMoney, String url) {
        this.marca = data.marca;
        this.ano = data.ano;
        this.placa = data.placa;
        this.capacidade = data.capacidade;
        this.cor = data.cor;
        this.categoria = data.categoria;
        this.quantidade = data.quantidade;
        // this.modelo = data.modelo;
        this.valor_diaria = formatMoney;
        this.url_imagem = url;
        this.agencia = data.agencia;
    }
}
