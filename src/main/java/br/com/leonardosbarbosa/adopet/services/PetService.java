package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.messages.ShelterErrorMessages;
import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.repositories.PetRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
            throw new DatabaseException(ShelterErrorMessages.NONEXISTENT_SHELTER_MESSAGE);
        }
    }
}
