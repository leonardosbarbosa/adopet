package br.com.leonardosbarbosa.adopet.factory;

import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.entities.Shelter;

public class PetFactory {

    public static Pet createPet() {
        Pet pet = new Pet();
        pet.setAdopted(false);
        pet.setName("Rex");
        pet.setDescription("Agitado e dócil");
        pet.setShelter(new Shelter(1L));
        return pet;
    }

    public static Pet createPetWithId() {
        Pet pet = createPet();
        pet.setId(1L);
        return pet;
    }

    public static PetDTO createPetDTORequest() {
        return new PetDTO(createPet());
    }

    public static PetDTO createPetDTOWithNonexistentShelter() {
        PetDTO pet = new PetDTO(createPet());
        pet.setShelterId(100L);
        return pet;
    }
}
