package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.dto.request.CreateTutorRequest;
import br.com.leonardosbarbosa.adopet.dto.response.CreateTutorResponse;
import br.com.leonardosbarbosa.adopet.entities.Tutor;
import br.com.leonardosbarbosa.adopet.repositories.TutorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.leonardosbarbosa.adopet.config.errors.messages.DefaultMessages.CREATE_RESOURCE_INTEGRITY_VIOLATION_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.TutorErrorMessages.NONEXISTENT_TUTOR_MESSAGE;
import static br.com.leonardosbarbosa.adopet.config.errors.messages.TutorErrorMessages.NONEXISTENT_TUTOR_TO_DELETE;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public TutorService(TutorRepository tutorRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.tutorRepository = tutorRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<TutorDTO> findAll(Pageable pageRequest) {
        Page<Tutor> tutorsPage = tutorRepository.findAll(pageRequest);

        return tutorsPage.map(TutorDTO::new);
    }

    public CreateTutorResponse createNew(CreateTutorRequest tutor) {
        try {
            Tutor tutorEntity = modelMapper.map(tutor, Tutor.class);
            tutorEntity.setPassword(passwordEncoder.encode(tutor.getPassword()));
            tutorEntity = tutorRepository.save(tutorEntity);
            return modelMapper.map(tutorEntity, CreateTutorResponse.class);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(CREATE_RESOURCE_INTEGRITY_VIOLATION_MESSAGE);
        }
    }

    public TutorDTO update(Long id, TutorDTO dto) {
        Tutor tutorEntity = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_TUTOR_MESSAGE));
        copyDtoToEntity(dto, tutorEntity);
        tutorEntity = tutorRepository.save(tutorEntity);
        return new TutorDTO(tutorEntity);
    }

    public void delete(Long id) {
        try {
            tutorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(NONEXISTENT_TUTOR_TO_DELETE);
        }
    }

    private void copyDtoToEntity(TutorDTO dto, Tutor entity) {

        if (dto.getFullName() != null) {
            entity.setFullName(dto.getFullName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getCity() != null) {
            entity.setCity(dto.getCity());
        }

        if (dto.getAbout() != null) {
            entity.setAbout(dto.getAbout());
        }
    }

    public TutorDTO findById(Long id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_TUTOR_MESSAGE));
        return new TutorDTO(tutor);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<TutorDetailsProjection> result = tutorRepository.searchUserAndRolesByEmail(username);
//        if (result.isEmpty())
//            throw new UsernameNotFoundException("User not found");
//
//        Tutor tutor = new Tutor();
//        tutor.setId(result.get(0).getId());
//        tutor.setEmail(result.get(0).getUsername());
//        tutor.setCity(result.get(0).getCity());
//        tutor.setFullName(result.get(0).getFullName());
//        tutor.setAbout(result.get(0).getAbout());
//        tutor.setEmail(result.get(0).getUsername());
//        tutor.setPhone(result.get(0).getPhone());
//        tutor.setProfilePic(result.get(0).getProfilePic());
//        result.forEach(r -> tutor.getRoles().add(new Role(r.getRoleId(), r.getAuthority())));
//
//
//        return tutor;
//    }
}
