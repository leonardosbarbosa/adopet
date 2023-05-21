package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.services.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tutors")
public class TutorResource {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    public ResponseEntity<Page<TutorDTO>> findAll(Pageable pageRequest) {
        return ResponseEntity.ok(tutorService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<TutorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TutorDTO> createNew(@RequestBody TutorDTO tutor) {
        tutor = tutorService.createNew(tutor);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tutor.getId()).toUri();

        return ResponseEntity.created(uri).body(tutor);
    }

    @PutMapping("{id}")
    public ResponseEntity<TutorDTO> updateById(@PathVariable Long id, @RequestBody TutorDTO tutor) {
        tutor = tutorService.update(id, tutor);

        return ResponseEntity.ok(tutor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        tutorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
