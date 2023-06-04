package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.dto.request.CreateShelterRequest;
import br.com.leonardosbarbosa.adopet.dto.request.UpdateShelterRequest;
import br.com.leonardosbarbosa.adopet.dto.response.ShelterResponse;
import br.com.leonardosbarbosa.adopet.services.ShelterService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/shelters")
public class ShelterResource {

    private final ShelterService shelterService;

    public ShelterResource(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping
    public ResponseEntity<Page<ShelterResponse>> findAll(Pageable pageRequest) {
        return ResponseEntity.ok(shelterService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<ShelterResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(shelterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShelterResponse> createNew(@RequestBody @Valid CreateShelterRequest shelter) {
        ShelterResponse createdShelter = shelterService.createNew(shelter);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdShelter.getId()).toUri();

        return ResponseEntity.created(uri).body(createdShelter);
    }

    @PutMapping("{id}")
    public ResponseEntity<ShelterResponse> updateById(@PathVariable Long id,
                                                      @RequestBody @Valid UpdateShelterRequest shelter) {
        ShelterResponse updatedShelter = shelterService.updateById(id, shelter);

        return ResponseEntity.ok(updatedShelter);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        shelterService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
