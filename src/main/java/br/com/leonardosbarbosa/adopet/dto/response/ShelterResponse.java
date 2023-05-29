package br.com.leonardosbarbosa.adopet.dto.response;

import br.com.leonardosbarbosa.adopet.entities.Pet;
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
    private final List<Pet> pets = new ArrayList<>();

    public ShelterResponse(Shelter shelter) {
        this.id = shelter.getId();
        this.name = shelter.getName();
        this.location = shelter.getLocation();

        if (!shelter.getPets().isEmpty())
            this.pets.addAll(shelter.getPets());
    }
}
