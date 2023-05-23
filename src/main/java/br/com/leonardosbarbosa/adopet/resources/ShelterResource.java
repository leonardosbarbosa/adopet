package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.dto.ShelterDTO;
import br.com.leonardosbarbosa.adopet.services.ShelterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/shelters")
public class ShelterResource {

    private final ShelterService shelterService;

    public ShelterResource(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping
    public ResponseEntity<Page<ShelterDTO>> findAll(Pageable pageRequest) {
        return ResponseEntity.ok(shelterService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<ShelterDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(shelterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShelterDTO> createNew(@RequestBody @Valid ShelterDTO shelter) {
        shelter = shelterService.createNew(shelter);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(shelter.getId()).toUri();

        return ResponseEntity.created(uri).body(shelter);
    }

    @PutMapping("{id}")
    public ResponseEntity<ShelterDTO> updateById(@PathVariable Long id, @RequestBody ShelterDTO shelter) {
        shelter = shelterService.updateById(id, shelter);

        return ResponseEntity.ok(shelter);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        shelterService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
