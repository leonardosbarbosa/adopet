package br.com.leonardosbarbosa.adopet.services;

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
    public void findByIdShouldReturnTutorDTO() {
        when(tutorRepository.findById(existentId)).thenReturn(Optional.of(tutor));
        doNothing().when(authService).validateSelfOrAdmin(anyLong());

        TutorDTO response = tutorService.findById(existentId);
        assertNotNull(response);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundException() {
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
    }
}
