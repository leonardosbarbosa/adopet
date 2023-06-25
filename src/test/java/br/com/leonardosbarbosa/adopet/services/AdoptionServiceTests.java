package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.AdoptionDTO;
import br.com.leonardosbarbosa.adopet.entities.Adoption;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.factory.AdoptionFactory;
import br.com.leonardosbarbosa.adopet.factory.PetFactory;
import br.com.leonardosbarbosa.adopet.repositories.AdoptionRepository;
import br.com.leonardosbarbosa.adopet.repositories.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdoptionServiceTests {

    @InjectMocks
    private AdoptionService adoptionService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private AdoptionRepository adoptionRepository;

    private final Page<Adoption> adoptionPage = new PageImpl<>(AdoptionFactory.adoptionListWith2Adoptions());
    private final Long existentId = 1L;
    private final Long nonexistentId = 2L;

    @Test
    public void findAllShouldReturnAllAdoptionsPaged() {
        when(adoptionRepository.findAll(any(PageRequest.class))).thenReturn(adoptionPage);

        Page<AdoptionDTO> result = adoptionService.findAll(PageRequest.of(1, 20));

        assertEquals(2, result.getSize());
    }

    @Test
    public void findAllShouldThrowResourceNotFoundExceptionWhenThereAreNoAdoptions() {
        when(adoptionRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class, () -> adoptionService.findAll(PageRequest.of(1, 10)));
    }

    @Test
    public void findByIdShouldReturnAdoptionDTOWhenExistentId() {
        Adoption adoption = AdoptionFactory.createAdoptionWithId();
        when(adoptionRepository.findById(existentId)).thenReturn(Optional.of(adoption));
        AdoptionDTO result = adoptionService.findById(existentId);

        assertNotNull(result);
        assertEquals(result.getId(), adoption.getId());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenNonexistentId() {
        when(adoptionRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> adoptionService.findById(nonexistentId));
    }

    @Test
    public void createNewAdoptionShouldReturnSuccessfulAdoption() {
        Pet pet = PetFactory.createPetWithId();
        Adoption adoption = AdoptionFactory.createAdoptionWithId();
        when(petRepository.findById(existentId)).thenReturn(Optional.of(pet));
        when(adoptionRepository.save(any(Adoption.class))).thenReturn(adoption);
        AdoptionDTO adoptionDTO = AdoptionFactory.createAdoptionDTO();

        var result = adoptionService.createNew(adoptionDTO);

        assertNotNull(result);
    }

    @Test
    public void createNewAdoptionShouldThrowDatabaseExceptionWhenDataIntegrityViolation() {
        doThrow(DataIntegrityViolationException.class).when(adoptionRepository).save(any(Adoption.class));

        AdoptionDTO adoptionDTO = AdoptionFactory.createAdoptionDTO();

        assertThrows(DatabaseException.class, () -> adoptionService.createNew(adoptionDTO));
    }

    @Test
    public void deleteByIdShouldDoNothingWhenExistentId() {
        doNothing().when(adoptionRepository).deleteById(existentId);
        when(petRepository.findById(existentId)).thenReturn(Optional.of(PetFactory.createPetWithId()));
        when(adoptionRepository.findPetIdByAdoptionId(existentId)).thenReturn(existentId);

        assertDoesNotThrow(() -> adoptionService.deleteById(existentId));
    }
}
