package br.fvc.api.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "Varchar(80)")
    private String nome;

    @Column(nullable = false , columnDefinition = "Varchar(80)")
    private String senha;

    @Column(nullable = false, unique = true, columnDefinition = "Varchar(14)")
    private String cpf;

    @Column(nullable = false, unique = true, columnDefinition = "Varchar(80)")
    private String email;

    @Column(nullable = false, columnDefinition = "Varchar(20)")
    private String role;

    @Column(nullable = false, unique = true, columnDefinition = "Varchar(14)")
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Address address;
}
