package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fvc.api.Models.Example;


public interface ExampleRepository extends JpaRepository<Example, Long> {

}
