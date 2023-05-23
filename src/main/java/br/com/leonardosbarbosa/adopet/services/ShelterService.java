package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.ShelterDTO;
import br.com.leonardosbarbosa.adopet.entities.Shelter;
import br.com.leonardosbarbosa.adopet.repositories.ShelterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.leonardosbarbosa.adopet.config.errors.messages.ShelterErrorMessages.NONEXISTENT_SHELTER_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.ShelterErrorMessages.NO_SHELTERS_REGISTERED_MESSAGE;

@Service
public class ShelterService {

    private final ModelMapper modelMapper;
    private final ShelterRepository shelterRepository;

    public ShelterService(ModelMapper modelMapper, ShelterRepository shelterRepository) {
        this.modelMapper = modelMapper;
        this.shelterRepository = shelterRepository;
    }

    public ShelterDTO createNew(ShelterDTO shelter) {
        Shelter shelterEntity = modelMapper.map(shelter, Shelter.class);
        shelterEntity = shelterRepository.save(shelterEntity);
        return modelMapper.map(shelterEntity, ShelterDTO.class);
    }

    public ShelterDTO findById(Long id) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(NONEXISTENT_SHELTER_MESSAGE));

        return modelMapper.map(shelter, ShelterDTO.class);
    }

    public Page<ShelterDTO> findAll(Pageable pageRequest) {
        Page<Shelter> sheltersPaged = shelterRepository.findAll(pageRequest);

        if (sheltersPaged.isEmpty())
            throw new ResourceNotFoundException(NO_SHELTERS_REGISTERED_MESSAGE);

        return sheltersPaged.map(ShelterDTO::new);
    }
}
