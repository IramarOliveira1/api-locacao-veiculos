package br.fvc.api.Models;

import java.sql.Date;

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
@Table(name = "reserva")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date data_reserva;

    @Column(nullable = false)
    private Date data_inicio_aluguel;

    @Column(nullable = false)
    private Date data_fim_aluguel;

    @Column(nullable = false)
    private Date data_entrega;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Vehicle veiculo;

    @ManyToOne
    @JoinColumn(name = "id_seguro", nullable = false)
    private Insurance insurance;
}
