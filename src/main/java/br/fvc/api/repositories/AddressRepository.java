package br.fvc.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}