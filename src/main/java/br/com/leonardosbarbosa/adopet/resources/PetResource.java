package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.services.PetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pets")
public class PetResource {

    private final PetService petService;

    public PetResource(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<Page<PetDTO>> findAll(Pageable pageRequest) {
        return ResponseEntity.ok(petService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable Long id) {
        PetDTO pet = petService.findById(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<PetDTO> createNew(@RequestBody @Valid PetDTO pet) {
        pet = petService.createNew(pet);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pet.getId()).toUri();
        return ResponseEntity.created(uri).body(pet);
    }

    @PutMapping("{id}")
    public ResponseEntity<PetDTO> updateById(@PathVariable Long id, @RequestBody PetDTO pet) {
        pet = petService.updateById(id, pet);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        petService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
