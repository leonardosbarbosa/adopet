package br.com.leonardosbarbosa.adopet.dto.response;

import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.entities.Shelter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterResponse {

    private Long id;
    private String name;
    private String location;
    private final List<PetDTO> pets = new ArrayList<>();

    public ShelterResponse(Shelter shelter) {
        this.id = shelter.getId();
        this.name = shelter.getName();
        this.location = shelter.getLocation();

        if (!shelter.getPets().isEmpty()) {
            shelter.getPets().forEach(pet -> this.pets.add(new PetDTO(pet)));
        }
    }
}
