package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.entities.Tutor;
import br.com.leonardosbarbosa.adopet.factory.TutorFactory;
import br.com.leonardosbarbosa.adopet.repositories.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TutorServiceTests {

    @InjectMocks
    private TutorService tutorService;
    @Mock
    private TutorRepository tutorRepository;

    @BeforeEach
    public void setUp() {
        Tutor tutor = TutorFactory.createTutor();
        Page<Tutor> page = new PageImpl<>(List.of(tutor));
        Mockito.when(tutorRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<TutorDTO> result = tutorService.findAll(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Mockito.verify(tutorRepository, times(1)).findAll(pageable);
    }
}
