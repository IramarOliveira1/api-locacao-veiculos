package br.fvc.api.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "veiculo")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

     @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private boolean disponibilidade;

    @Column(nullable = false)
    private double valor_diaria;

    @Column(nullable = false)
    private String url_imagem;

    @ManyToOne
    @JoinColumn(name = "id_agencia", nullable = false) 
    private Agency agencia;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false) 
    private Category categoria;
}
