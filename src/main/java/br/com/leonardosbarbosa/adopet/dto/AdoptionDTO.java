package br.com.leonardosbarbosa.adopet.dto;

import br.com.leonardosbarbosa.adopet.entities.Adoption;
import lombok.Data;

import java.time.Instant;

@Data
public class AdoptionDTO {

    private Long id;
    private Long petId;
    private Long tutorId;
    private Instant date;

    public AdoptionDTO(Adoption adoption) {
        this.id = adoption.getId();
        this.petId = adoption.getPet().getId();
        this.tutorId = adoption.getTutor().getId();
        this.date = adoption.getDate();
    }
}
