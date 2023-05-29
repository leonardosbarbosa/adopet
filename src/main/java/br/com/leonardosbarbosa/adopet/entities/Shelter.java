package br.com.leonardosbarbosa.adopet.entities;

import br.com.leonardosbarbosa.adopet.dto.request.UpdateShelterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_shelter")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    @OneToMany(mappedBy = "shelter", fetch = FetchType.EAGER)
    private final List<Pet> pets = new ArrayList<>();

    public Shelter(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public void updateFields(UpdateShelterRequest shelterDTO) {

        if (shelterDTO.getName() != null) {
            this.name = shelterDTO.getName();
        }
        if (shelterDTO.getLocation() != null) {
            this.location = shelterDTO.getLocation();
        }
    }
}
