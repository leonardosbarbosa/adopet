package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.BusinessException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.DatabaseException;
import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.entities.Pet;
import br.com.leonardosbarbosa.adopet.factory.PetFactory;
import br.com.leonardosbarbosa.adopet.repositories.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTests {

    @InjectMocks
    PetService petService;
    @Mock
    PetRepository petRepository;

    private final Pet savedPet = PetFactory.createPetWithId();
    private final Long existentId = 1L;
    private final Long nonexistentId = 2L;
    private final Page<Pet> page = new PageImpl<>(List.of(savedPet));

    @Test
    public void findAllShouldReturnAllPetsPaged() {
        when(petRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
        Pageable pageable = PageRequest.of(0, 20);
        Page<PetDTO> result = petService.findAll(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Mockito.verify(petRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnPetDTOWhenExistentId() {
        when(petRepository.findById(existentId)).thenReturn(Optional.of(savedPet));
        PetDTO result = petService.findById(existentId);

        assertNotNull(result);
        assertEquals(result.getId(), savedPet.getId());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenNonexistentId() {
        when(petRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> petService.findById(nonexistentId));
    }

    @Test
    public void createNewPetShouldReturnPetDTO() {
        PetDTO petRequest = PetFactory.createPetDTORequest();
        Pet newPet = new Pet(petRequest);
        when(petRepository.save(newPet)).thenReturn(savedPet);

        PetDTO result = petService.createNew(petRequest);

        assertNotNull(newPet);
        assertEquals(result.getId(), savedPet.getId());
        verify(petRepository, times(1)).save(newPet);
    }

    @Test
    public void createNewPetShouldThrowDataBaseExceptionWhenDataIntegrityViolationException() {
        PetDTO petRequest = PetFactory.createPetDTOWithNonexistentShelter();
        Pet newPetWithNonExistentShelter = new Pet(petRequest);
        doThrow(DataIntegrityViolationException.class).when(petRepository).save(newPetWithNonExistentShelter);

        assertThrows(DatabaseException.class, () -> petService.createNew(petRequest));
    }

    @Test
    public void updatePetByIdShouldReturnPetWhenValidUpdateFields() {
        PetDTO pet = new PetDTO(PetFactory.createPet());
        when(petRepository.findById(existentId)).thenReturn(Optional.of(savedPet));
        when(petRepository.save(any(Pet.class))).thenReturn(savedPet);

        PetDTO response = petService.updateById(existentId, pet);

        assertNotNull(response);
    }

    @Test
    public void updatePetByIdShouldThrowResourceNotFoundExceptionWhenNonExistentId() {
        PetDTO pet = new PetDTO(PetFactory.createPet());

        when(petRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> petService.updateById(nonexistentId, pet));
    }

    @Test
    public void updatePetByIdShouldThrowBusinessExceptionWhenInvalidShelter() {
        PetDTO petWithInvalidShelterId = new PetDTO(PetFactory.createPet());
        Long nonexistentShelterId = 200L;
        petWithInvalidShelterId.setShelterId(nonexistentShelterId);


        when(petRepository.findById(existentId)).thenReturn(Optional.of(savedPet));
        doThrow(JpaObjectRetrievalFailureException.class).when(petRepository).save(savedPet);

        assertThrows(BusinessException.class, () -> petService.updateById(existentId, petWithInvalidShelterId));
    }

    @Test
    public void deletePetByIdShouldDoNothingWhenExistentId() {
        doNothing().when(petRepository).deleteById(existentId);

        assertDoesNotThrow(() -> petRepository.deleteById(existentId));
    }

    @Test
    public void deleteByIdShouldThrowDatabaseExceptionWhenDataIntegrityViolation() {
        Long petIdBelongingToAShelter = 3L;
        doThrow(DataIntegrityViolationException.class).when(petRepository).deleteById(petIdBelongingToAShelter);

        assertThrows(DatabaseException.class, () -> petService.deleteById(petIdBelongingToAShelter));
    }

}
