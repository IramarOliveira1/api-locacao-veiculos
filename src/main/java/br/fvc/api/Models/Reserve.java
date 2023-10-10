package br.fvc.api.Models;

import java.sql.Date;
import java.time.LocalDate;

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
@Entity(name = "reserva")
@Table(name = "reserva")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data_reserva;

    @Column(nullable = false)
    private Date data_inicio_aluguel;

    @Column(nullable = false)
    private Date data_fim_aluguel;

    @Column(nullable = false, columnDefinition = "Varchar(80)")
    private String status;

    @Column(nullable = false)
    private Long codigo_reserva;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Vehicle veiculo;

    @ManyToOne
    @JoinColumn(name = "id_seguro", nullable = false)
    private Insurance seguro;

    @ManyToOne
    @JoinColumn(name = "id_pagamento", nullable = false)
    private Payment pagamento;

    @ManyToOne
    @JoinColumn(name = "id_agencia_retirada", nullable = false)
    private Agency agenciaRetirada;

    @ManyToOne
    @JoinColumn(name = "id_agencia_devolucao", nullable = false)
    private Agency agenciaDevolucao;
}
