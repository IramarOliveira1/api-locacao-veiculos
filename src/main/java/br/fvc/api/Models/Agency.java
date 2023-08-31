package br.fvc.api.Models;

import br.fvc.api.Domain.Agency.AgencyRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "agencia")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "Varchar(80)")
    private String nome;

    @Column(nullable = false, unique = true, columnDefinition = "Varchar(15)")
    private String telefone;

    @Column
    private String quantidade_total;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false, referencedColumnName = "id")
    private Address address;

    public Agency(AgencyRequestDTO data){
        this.nome = data.nome;
        this.telefone = data.telefone;
        this.quantidade_total = data.quantidade_total;
    }
}
