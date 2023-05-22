package br.com.leonardosbarbosa.adopet.repositories;

import br.com.leonardosbarbosa.adopet.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
