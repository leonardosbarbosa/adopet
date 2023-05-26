package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.AdoptionDTO;
import br.com.leonardosbarbosa.adopet.entities.Adoption;
import br.com.leonardosbarbosa.adopet.repositories.AdoptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.leonardosbarbosa.adopet.config.errors.messages.AdoptionErrorMessages.NONEXISTENT_ADOPTION_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.AdoptionErrorMessages.NO_ADOPTIONS_REGISTERED_MESSAGE;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;

    public AdoptionService(AdoptionRepository adoptionRepository) {
        this.adoptionRepository = adoptionRepository;
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
}
