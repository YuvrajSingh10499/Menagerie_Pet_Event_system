package com.petEvent.petEvent.Repository;

import com.petEvent.petEvent.Entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findBySpecies(String species);
}
