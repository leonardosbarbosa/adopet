package br.com.leonardosbarbosa.adopet.entities;

import br.com.leonardosbarbosa.adopet.dto.request.UpdateShelterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_shelter")
@NoArgsConstructor
@Data
public class Shelter extends User {

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
