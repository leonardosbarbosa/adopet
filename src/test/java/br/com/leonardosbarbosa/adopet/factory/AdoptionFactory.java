package br.com.leonardosbarbosa.adopet.factory;

import br.com.leonardosbarbosa.adopet.dto.AdoptionDTO;
import br.com.leonardosbarbosa.adopet.entities.Adoption;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.entities.Tutor;

import java.time.Instant;
import java.util.List;

public class AdoptionFactory {


    public static Adoption createAdoptionWithId() {
        Pet pet1 = PetFactory.createPetWithId();
        Tutor tutor1 = TutorFactory.createTutorWithId();

        return new Adoption(1L, pet1, tutor1, Instant.now());
    }

    public static List<Adoption> adoptionListWith2Adoptions() {
        Pet pet1 = PetFactory.createPetWith(1L, "Rex", "Dócil e calmo");
        Tutor tutor1 = TutorFactory.createTutorWith(1L, "Leo Barbosa", null, null, null);

        Pet pet2 = PetFactory.createPetWith(2L, "Jimmy", "Agitado e companheiro");
        Tutor tutor2 = TutorFactory.createTutorWith(2L, "Caroline Oliveira", null, null, null);

        Adoption adoption1 = new Adoption(1L, pet1, tutor1, Instant.now());
        Adoption adoption2 = new Adoption(2L, pet2, tutor2, Instant.now());

        return List.of(adoption1, adoption2);
    }

    public static AdoptionDTO createAdoptionDTO() {
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setPetId(1L);
        adoptionDTO.setTutorId(1L);
        return adoptionDTO;
    }
}
