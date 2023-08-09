package br.fvc.api.Models;


import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pagamento")
public class Payment {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private Date data_pagamento;

    @Column(nullable = false)
    private String tipo_pagamento;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String url_pdf;    

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @OneToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserve reserve;

}
