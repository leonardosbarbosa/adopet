package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.dto.request.CreateTutorRequest;
import br.com.leonardosbarbosa.adopet.dto.response.CreateTutorResponse;
import br.com.leonardosbarbosa.adopet.entities.Tutor;
import br.com.leonardosbarbosa.adopet.factory.TutorFactory;
import br.com.leonardosbarbosa.adopet.repositories.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TutorServiceTests {

    @InjectMocks
    private TutorService tutorService;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final Tutor tutor = TutorFactory.createTutorWithId();
    private final Long existentId = 1L;
    private final Long nonexistentId = 2L;
    private final Page<Tutor> page = new PageImpl<>(List.of(tutor));


    @Test
    public void findAllShouldReturnPage() {
        when(tutorRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
        Pageable pageable = PageRequest.of(0, 20);
        Page<TutorDTO> result = tutorService.findAll(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Mockito.verify(tutorRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnTutorDTOWhenIdExists() {
        when(tutorRepository.findById(existentId)).thenReturn(Optional.of(tutor));
        doNothing().when(authService).validateSelfOrAdmin(anyLong());

        TutorDTO response = tutorService.findById(existentId);
        assertNotNull(response);
        assertEquals(response.getFullName(), tutor.getFullName());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        when(tutorRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tutorService.findById(nonexistentId));
    }

    @Test
    public void createTutorShouldReturnCreatedTutor() {
        CreateTutorRequest newTutorDTO = TutorFactory.createTutorRequest();
        Tutor newTutorEntity = TutorFactory.createTutorWithId();
        when(tutorRepository.save(any(Tutor.class))).thenReturn(newTutorEntity);
        when(passwordEncoder.encode(anyString())).thenReturn(newTutorEntity.getPassword());

        CreateTutorResponse response = tutorService.createNew(newTutorDTO);

        assertNotNull(response);
        assertEquals(response.getFullName(), newTutorEntity.getFullName());
    }

    @Test
    public void createTutorShouldThrowDatabaseExceptionWhenExistentEmail() {
        CreateTutorRequest newTutorDTO = TutorFactory.createTutorRequest();
        Tutor tutorWithExistentEmail = TutorFactory.createTutorWithId();
        when(tutorRepository.save(any(Tutor.class))).thenThrow(DataIntegrityViolationException.class);
        when(passwordEncoder.encode(anyString())).thenReturn(tutorWithExistentEmail.getPassword());

        assertThrows(DatabaseException.class, () -> tutorService.createNew(newTutorDTO));
    }

    @Test
    public void updateTutorByIdShouldReturnUpdatedTutorWhenExistentId() {
        TutorDTO tutorDTO = TutorFactory.createTutorDTO();
        Tutor existentTutor = TutorFactory.createTutorWithId();
        when(tutorRepository.findById(existentId)).thenReturn(Optional.of(existentTutor));
        when(tutorRepository.save(any(Tutor.class))).thenReturn(existentTutor);

        tutorDTO = tutorService.update(existentId, tutorDTO);

        assertNotNull(tutorDTO);
        assertEquals(tutorDTO.getFullName(), existentTutor.getFullName());
    }

    @Test
    public void updateTutorByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        TutorDTO tutorDTO = TutorFactory.createTutorDTO();

        when(tutorRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tutorService.update(nonexistentId, tutorDTO));
    }

    @Test
    public void deleteTutorByIdShouldDoNothingWhenExistentId() {
        doNothing().when(tutorRepository).deleteById(existentId);

        assertDoesNotThrow(() -> tutorService.delete(existentId));
    }

    @Test
    public void deleteTutorByIdShouldThrowResourceNotFoundExceptionWhenNonExistentId() {
        doThrow(EmptyResultDataAccessException.class).when(tutorRepository).deleteById(nonexistentId);

        assertThrows(ResourceNotFoundException.class, () -> tutorService.delete(nonexistentId));
    }
}
