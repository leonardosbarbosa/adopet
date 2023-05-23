package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.repositories.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    public PetService(PetRepository petRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }

    public PetDTO createNew(PetDTO pet) {
        Pet entity = modelMapper.map(pet, Pet.class);
        entity = petRepository.save(entity);
        return modelMapper.map(entity, PetDTO.class);
    }
}
