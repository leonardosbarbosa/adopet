package br.com.leonardosbarbosa.adopet.entities;

import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.dto.request.CreateTutorRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static br.com.leonardosbarbosa.adopet.services.enums.RolesEnum.TUTOR;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_tutor")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tutor extends User {

    @Column(nullable = false)
    private String fullName;
    private String phone;
    private String city;
    private String about;
    private String profilePic;
    @OneToMany(mappedBy = "tutor", fetch = FetchType.EAGER)
    private final List<Adoption> adoptions = new ArrayList<>();

    public Tutor(TutorDTO tutor) {
        this.setId(tutor.getId());
        this.fullName = tutor.getFullName();
        this.setEmail(tutor.getEmail());
        this.setPassword(tutor.getPassword());
        this.phone = tutor.getPhone();
        this.city = tutor.getCity();
        this.about = tutor.getAbout();
    }

    public Tutor(CreateTutorRequest tutor) {
        this.fullName = tutor.getFullName();
        this.setEmail(tutor.getEmail());
        this.setPassword(tutor.getPassword());
        this.addRole(new Role(TUTOR.code));
    }
}
