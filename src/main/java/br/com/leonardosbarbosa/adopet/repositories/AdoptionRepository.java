package br.com.leonardosbarbosa.adopet.repositories;

import br.com.leonardosbarbosa.adopet.entities.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}
