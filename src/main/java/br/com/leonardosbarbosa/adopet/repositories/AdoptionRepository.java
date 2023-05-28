package br.com.leonardosbarbosa.adopet.repositories;

import br.com.leonardosbarbosa.adopet.entities.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    @Query(value = "SELECT a.pet.id FROM Adoption a WHERE a.id = :id")
    Long findPetIdByAdoptionId(Long id);
}
