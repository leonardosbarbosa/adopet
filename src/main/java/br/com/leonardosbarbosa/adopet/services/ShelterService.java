package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.dto.ShelterDTO;
import br.com.leonardosbarbosa.adopet.entities.Shelter;
import br.com.leonardosbarbosa.adopet.repositories.ShelterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
