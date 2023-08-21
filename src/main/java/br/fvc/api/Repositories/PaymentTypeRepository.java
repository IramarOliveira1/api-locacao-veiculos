package br.fvc.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.Models.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    Boolean existsByTipo(String tipo);

    List<PaymentType> findByTipoContainingIgnoreCase(String filtro);
}