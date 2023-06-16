package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.request.CreateShelterRequest;
import br.com.leonardosbarbosa.adopet.dto.request.UpdateShelterRequest;
import br.com.leonardosbarbosa.adopet.dto.response.ShelterResponse;
import br.com.leonardosbarbosa.adopet.entities.Shelter;
import br.com.leonardosbarbosa.adopet.factory.ShelterFactory;
import br.com.leonardosbarbosa.adopet.repositories.ShelterRepository;
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
public class ShelterServiceTests {

    @InjectMocks
    private ShelterService shelterService;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final Shelter shelter = ShelterFactory.createShelterWithId();
    private final Long existentId = 1L;
    private final Long nonexistentId = 2L;
    private final Page<Shelter> page = new PageImpl<>(List.of(shelter));


    @Test
    public void findAllShouldReturnPage() {
        when(shelterRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
        Pageable pageable = PageRequest.of(0, 20);
        Page<ShelterResponse> result = shelterService.findAll(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Mockito.verify(shelterRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnShelterDTOWhenIdExists() {
        when(shelterRepository.findById(existentId)).thenReturn(Optional.of(shelter));
        doNothing().when(authService).validateSelfOrAdmin(anyLong());

        ShelterResponse response = shelterService.findById(existentId);
        assertNotNull(response);
        assertEquals(response.getName(), shelter.getName());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        when(shelterRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shelterService.findById(nonexistentId));
    }

    @Test
    public void createShelterShouldReturnCreatedShelter() {
        CreateShelterRequest newShelterDTO = ShelterFactory.createShelterRequest();
        Shelter newShelterEntity = ShelterFactory.createShelterWithId();
        when(shelterRepository.save(any(Shelter.class))).thenReturn(newShelterEntity);
        when(passwordEncoder.encode(anyString())).thenReturn(newShelterEntity.getPassword());

        ShelterResponse response = shelterService.createNew(newShelterDTO);

        assertNotNull(response);
        assertEquals(response.getName(), newShelterEntity.getName());
    }

    @Test
    public void createShelterShouldThrowDatabaseExceptionWhenExistentEmail() {
        CreateShelterRequest newShelterDTO = ShelterFactory.createShelterRequest();
        Shelter shelterWithExistentEmail = ShelterFactory.createShelterWithId();
        when(shelterRepository.save(any(Shelter.class))).thenThrow(DataIntegrityViolationException.class);
        when(passwordEncoder.encode(anyString())).thenReturn(shelterWithExistentEmail.getPassword());

        assertThrows(DatabaseException.class, () -> shelterService.createNew(newShelterDTO));
    }

    @Test
    public void updateShelterByIdShouldReturnUpdatedShelterWhenExistentId() {
        UpdateShelterRequest shelterDTO = ShelterFactory.createUpdateShelterRequest();
        Shelter existentShelter = ShelterFactory.createShelterWithId();
        when(shelterRepository.findById(existentId)).thenReturn(Optional.of(existentShelter));
        when(shelterRepository.save(any(Shelter.class))).thenReturn(existentShelter);

        ShelterResponse response = shelterService.updateById(existentId, shelterDTO);

        assertNotNull(response);
        assertEquals(response.getName(), existentShelter.getName());
    }

    @Test
    public void updateShelterByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        UpdateShelterRequest shelterDTO = ShelterFactory.createUpdateShelterRequest();

        when(shelterRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shelterService.updateById(nonexistentId, shelterDTO));
    }

    @Test
    public void deleteShelterByIdShouldDoNothingWhenExistentId() {
        doNothing().when(shelterRepository).deleteById(existentId);

        assertDoesNotThrow(() -> shelterService.deleteById(existentId));
    }

    @Test
    public void deleteShelterByIdShouldThrowResourceNotFoundExceptionWhenNonExistentId() {
        doThrow(EmptyResultDataAccessException.class).when(shelterRepository).deleteById(nonexistentId);

        assertThrows(ResourceNotFoundException.class, () -> shelterService.deleteById(nonexistentId));
    }
}
