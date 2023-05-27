package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.dto.AdoptionDTO;
import br.com.leonardosbarbosa.adopet.services.AdoptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/adoptions")
public class AdoptionResource {

    private final AdoptionService service;

    public AdoptionResource(AdoptionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<AdoptionDTO>> findAll(Pageable pageRequest) {
        Page<AdoptionDTO> adoptions = service.findAll(pageRequest);
        return ResponseEntity.ok(adoptions);
    }

    @GetMapping("{id}")
    public ResponseEntity<AdoptionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AdoptionDTO> createNew(@RequestBody AdoptionDTO adoption) {

        adoption = service.createNew(adoption);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(adoption.getId()).toUri();

        return ResponseEntity.created(uri).body(adoption);
    }
}
