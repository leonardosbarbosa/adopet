package br.com.leonardosbarbosa.adopet.dto;

import br.com.leonardosbarbosa.adopet.entities.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    private final List<Pet> pets = new ArrayList<>();
}
