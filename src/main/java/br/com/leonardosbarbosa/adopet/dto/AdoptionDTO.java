package br.com.leonardosbarbosa.adopet.dto;

import br.com.leonardosbarbosa.adopet.entities.Adoption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionDTO {

    private Long id;
    @NotNull
    private Long petId;
    @NotNull
    private Long tutorId;
    private Instant date;

    public AdoptionDTO(Adoption adoption) {
        this.id = adoption.getId();
        this.petId = adoption.getPet().getId();
        this.tutorId = adoption.getTutor().getId();
        this.date = adoption.getDate();
    }
}
