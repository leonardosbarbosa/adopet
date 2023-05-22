package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.dto.ShelterDTO;
import br.com.leonardosbarbosa.adopet.services.ShelterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    public ResponseEntity<ShelterDTO> createNew(@RequestBody @Valid ShelterDTO shelter) {
        shelter = shelterService.createNew(shelter);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(shelter.getId()).toUri();

        return ResponseEntity.created(uri).body(shelter);
    }
}
