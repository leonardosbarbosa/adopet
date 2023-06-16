package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.dto.request.CreateTutorRequest;
import br.com.leonardosbarbosa.adopet.dto.response.CreateTutorResponse;
import br.com.leonardosbarbosa.adopet.entities.Tutor;
import br.com.leonardosbarbosa.adopet.repositories.TutorRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public TutorService(TutorRepository tutorRepository, PasswordEncoder passwordEncoder,
                        AuthService authService) {
        this.tutorRepository = tutorRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    public Page<TutorDTO> findAll(Pageable pageRequest) {
        Page<Tutor> tutorsPage = tutorRepository.findAll(pageRequest);

        return tutorsPage.map(TutorDTO::new);
    }

    public TutorDTO findById(Long id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_TUTOR_MESSAGE));
        authService.validateSelfOrAdmin(tutor.getId());
        return new TutorDTO(tutor);
    }

    public CreateTutorResponse createNew(CreateTutorRequest tutor) {
        try {
            tutor.setPassword(passwordEncoder.encode(tutor.getPassword()));
            Tutor tutorEntity = new Tutor(tutor);
            tutorEntity = tutorRepository.save(tutorEntity);
            return new CreateTutorResponse(tutorEntity);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(CREATE_RESOURCE_INTEGRITY_VIOLATION_MESSAGE);
        }
    }

    public TutorDTO updateById(Long id, TutorDTO dto) {
        Tutor tutorEntity = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NONEXISTENT_TUTOR_MESSAGE));
        copyDtoToEntity(dto, tutorEntity);
        tutorEntity = tutorRepository.save(tutorEntity);
        return new TutorDTO(tutorEntity);
    }

    public void deleteById(Long id) {
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
}
