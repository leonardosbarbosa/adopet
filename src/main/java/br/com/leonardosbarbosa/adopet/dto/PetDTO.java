package br.com.leonardosbarbosa.adopet.dto;

import br.com.leonardosbarbosa.adopet.entities.Pet;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class PetDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Integer age;
    private Boolean adopted;
    @NotBlank
    private String image;
    @NotNull
    private Long shelterId;

    public PetDTO(Pet entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.age = entity.getAge();
        this.adopted = entity.getAdopted();
        this.image = entity.getImage();
        this.shelterId = entity.getShelter().getId();
    }
}
