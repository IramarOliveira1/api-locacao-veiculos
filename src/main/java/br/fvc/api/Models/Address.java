package br.fvc.api.Models;

import br.fvc.api.Domain.Address.AddressDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
@Table(name = "endereco")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Address(AddressDTO data) {
        this.cep = data.zipcode;
        this.bairro = data.neighborhood;
        this.cidade = data.city;
        this.complemento = data.complement;
        this.logradouro = data.address;
        this.numero = data.number;
        this.uf = data.uf;
    }

}
