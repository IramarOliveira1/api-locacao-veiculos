package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.Models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}