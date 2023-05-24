package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.repositories.PetRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.leonardosbarbosa.adopet.config.errors.messages.PetErrorMessages.NO_PETS_REGISTERED_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.ShelterErrorMessages.NONEXISTENT_SHELTER_MESSAGE;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetDTO createNew(PetDTO pet) {
        try {
            Pet entity = new Pet(pet);
            entity = petRepository.save(entity);
            return new PetDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(NONEXISTENT_SHELTER_MESSAGE);
        }
    }

    public Page<PetDTO> findAll(Pageable pageRequest) {
        Page<Pet> pets = petRepository.findAll(pageRequest);

        if (pets.isEmpty())
            throw new ResourceNotFoundException(NO_PETS_REGISTERED_MESSAGE);

        return pets.map(PetDTO::new);
    }
}
