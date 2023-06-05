package br.com.leonardosbarbosa.adopet.entities;

import br.com.leonardosbarbosa.adopet.dto.request.UpdateShelterRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    public Shelter(String email, String password, String name, String location) {
        super(email, password);
        this.name = name;
        this.location = location;
        this.getRoles().add(new Role(2L));
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
