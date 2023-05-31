package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.AdoptionDTO;
import br.com.leonardosbarbosa.adopet.entities.Adoption;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.repositories.AdoptionRepository;
import br.com.leonardosbarbosa.adopet.repositories.PetRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static br.com.leonardosbarbosa.adopet.config.errors.messages.AdoptionErrorMessages.NONEXISTENT_ADOPTION_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.AdoptionErrorMessages.NO_ADOPTIONS_REGISTERED_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.DefaultMessages.CREATE_RESOURCE_INTEGRITY_VIOLATION_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.DefaultMessages.DELETE_RESOURCE_INTEGRITY_VIOLATION_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.PetErrorMessages.NONEXISTENT_PET_MESSAGE;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final PetRepository petRepository;

    public AdoptionService(AdoptionRepository adoptionRepository, PetRepository petRepository) {
        this.adoptionRepository = adoptionRepository;
        this.petRepository = petRepository;
    }

    public Page<AdoptionDTO> findAll(Pageable pageRequest) {
        Page<Adoption> adoptions = adoptionRepository.findAll(pageRequest);

        if (adoptions.isEmpty())
            throw new ResourceNotFoundException(NO_ADOPTIONS_REGISTERED_MESSAGE);

        return adoptions.map(AdoptionDTO::new);
    }

    public AdoptionDTO findById(Long id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_ADOPTION_MESSAGE));

        return new AdoptionDTO(adoption);
    }

    public AdoptionDTO createNew(AdoptionDTO adoption) {
        try {
            Adoption entity = new Adoption(adoption);
            entity.setDate(Instant.now());
            entity = adoptionRepository.save(entity);
            setPetAdoptionStatus(entity.getPet().getId(), true);
            return new AdoptionDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(CREATE_RESOURCE_INTEGRITY_VIOLATION_MESSAGE);
        }
    }

    public void deleteById(Long id) {
        try {
            Long petId = adoptionRepository.findPetIdByAdoptionId(id);
            setPetAdoptionStatus(petId, false);
            adoptionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(NONEXISTENT_ADOPTION_MESSAGE);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(DELETE_RESOURCE_INTEGRITY_VIOLATION_MESSAGE);
        }
    }

    public void setPetAdoptionStatus(Long petId, boolean isAdopted) {
        Pet adoptedPet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_PET_MESSAGE));
        adoptedPet.setAdopted(isAdopted);
        petRepository.save(adoptedPet);
    }
}
