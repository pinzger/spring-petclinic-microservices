package org.springframework.samples.petclinic.vets.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
}
