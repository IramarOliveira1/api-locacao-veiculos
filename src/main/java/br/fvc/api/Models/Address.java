package br.fvc.api.Models;

import br.fvc.api.Domain.Address.AddressDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "endereco")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "Varchar(9)")
    private String cep;

    @Column(nullable = false, columnDefinition = "Varchar(80)")
    private String logradouro;

    @Column(columnDefinition = "Varchar(80)")
    private String complemento;

    @Column(nullable = false, columnDefinition = "Varchar(2)")
    private String uf;

    @Column(nullable = false, columnDefinition = "Varchar(80)")
    private String bairro;

    @Column(nullable = false, columnDefinition = "Varchar(50)")
    private String cidade;

    @Column(nullable = false)
    private int numero;

    @OneToOne(mappedBy = "address")
    private User user;
}
