package br.fvc.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fvc.api.Models.Example;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {

}
