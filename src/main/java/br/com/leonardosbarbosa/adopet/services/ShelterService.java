package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.request.CreateShelterRequest;
import br.com.leonardosbarbosa.adopet.dto.request.UpdateShelterRequest;
import br.com.leonardosbarbosa.adopet.dto.response.ShelterResponse;
import br.com.leonardosbarbosa.adopet.entities.Role;
import br.com.leonardosbarbosa.adopet.entities.Shelter;
import br.com.leonardosbarbosa.adopet.repositories.ShelterRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.leonardosbarbosa.adopet.config.errors.messages.ShelterErrorMessages.NONEXISTENT_SHELTER_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.ShelterErrorMessages.NO_SHELTERS_REGISTERED_MESSAGE;
import static br.com.leonardosbarbosa.adopet.services.enums.RolesEnum.SHELTER;

@Service
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final PasswordEncoder passwordEncoder;

    public ShelterService(ShelterRepository shelterRepository, PasswordEncoder passwordEncoder) {
        this.shelterRepository = shelterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ShelterResponse createNew(CreateShelterRequest shelter) {
        Shelter newShelter = new Shelter(shelter.getEmail(), passwordEncoder.encode(shelter.getPassword()),
                shelter.getName(), shelter.getLocation());

        newShelter.addRole(new Role(SHELTER.code));
        newShelter = shelterRepository.save(newShelter);
        return new ShelterResponse(newShelter);
    }

    public ShelterResponse findById(Long id) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_SHELTER_MESSAGE));

        return new ShelterResponse(shelter);
    }

    public Page<ShelterResponse> findAll(Pageable pageRequest) {
        Page<Shelter> sheltersPaged = shelterRepository.findAll(pageRequest);

        if (sheltersPaged.isEmpty())
            throw new ResourceNotFoundException(NO_SHELTERS_REGISTERED_MESSAGE);

        return sheltersPaged.map(ShelterResponse::new);
    }

    public ShelterResponse updateById(Long id, UpdateShelterRequest shelter) {
        Shelter shelterEntity = shelterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_SHELTER_MESSAGE));

        shelterEntity.updateFields(shelter);
        shelterEntity = shelterRepository.save(shelterEntity);

        return new ShelterResponse(shelterEntity);
    }

    public void deleteById(Long id) {
        try {
            shelterRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(NONEXISTENT_SHELTER_MESSAGE);
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<ShelterDetailsProjection> result = shelterRepository.searchUserAndRolesByEmail(username);
//        if (result.isEmpty())
//            throw new UsernameNotFoundException("User not found");
//
//        Shelter shelter = new Shelter();
//        shelter.setId(result.get(0).getId());
//        shelter.setEmail(result.get(0).getUsername());
//        shelter.setName(result.get(0).getName());
//        shelter.setLocation(result.get(0).getLocation());
//
//        result.forEach(r -> shelter.getRoles().add(new Role(r.getRoleId(), r.getAuthority())));
//
//
//        return shelter;
//    }
}
