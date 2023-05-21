package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DuplicatedEmailException;
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
import org.springframework.stereotype.Service;

import static br.com.leonardosbarbosa.adopet.config.errors.messages.TutorErrorMessages.*;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    private final ModelMapper modelMapper;

    public TutorService(TutorRepository tutorRepository, ModelMapper modelMapper) {
        this.tutorRepository = tutorRepository;
        this.modelMapper = modelMapper;
    }

    public Page<TutorDTO> findAll(Pageable pageRequest) {
        Page<Tutor> tutorsPage = tutorRepository.findAll(pageRequest);

        return tutorsPage.map(TutorDTO::new);
    }

    public CreateTutorResponse createNew(CreateTutorRequest tutor) {
        try {
            Tutor tutorEntity = modelMapper.map(tutor, Tutor.class);
            tutorEntity = tutorRepository.save(tutorEntity);
            return modelMapper.map(tutorEntity, CreateTutorResponse.class);

        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException(DUPLICATED_TUTOR_EMAIL);
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
}
