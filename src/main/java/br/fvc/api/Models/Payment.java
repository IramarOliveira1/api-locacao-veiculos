package br.fvc.api.Models;

import java.math.BigDecimal;
import java.sql.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false, columnDefinition="Decimal(10,2)")
    private BigDecimal preco;

    @Column(nullable = false)
    private Date data_pagamento;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String url_pdf;

    @OneToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserve reserve;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pagamento", nullable = false)
    PaymentType paymentType;
}
