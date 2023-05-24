package br.com.leonardosbarbosa.adopet.entities;

import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_pet")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Boolean adopted;
    @Column(nullable = false)
    private String image;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public Pet(PetDTO pet) {
        this.name = pet.getName();
        this.description = pet.getDescription();
        this.age = pet.getAge();
        this.adopted = false;
        this.image = pet.getImage();
        this.shelter = new Shelter();
        this.shelter.setId(pet.getShelterId());
    }

    public void updateFields(PetDTO pet) {
        if (pet.getName() != null)
            this.name = pet.getName();
        if (pet.getDescription() != null)
            this.description = pet.getDescription();
        if (pet.getAge() != null)
            this.age = pet.getAge();
        if (pet.getAdopted() != null)
            this.adopted = pet.getAdopted();
        if (pet.getImage() != null)
            this.image = pet.getImage();

        if (pet.getShelterId() != null) {
            this.shelter = new Shelter();
            this.shelter.setId(pet.getShelterId());
        }
    }
}
