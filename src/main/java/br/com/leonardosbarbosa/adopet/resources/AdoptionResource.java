package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.dto.AdoptionDTO;
import br.com.leonardosbarbosa.adopet.services.AdoptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
