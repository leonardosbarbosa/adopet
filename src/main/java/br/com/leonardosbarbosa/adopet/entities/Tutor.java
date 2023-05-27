package br.com.leonardosbarbosa.adopet.entities;

import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tutor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phone;
    private String city;
    private String about;
    private String profilePic;
    @OneToMany(mappedBy = "tutor")
    private final List<Adoption> adoptions = new ArrayList<>();

    public Tutor(TutorDTO tutor) {
        this.id = tutor.getId();
        this.fullName = tutor.getFullName();
        this.email = tutor.getEmail();
        this.password = tutor.getPassword();
        this.phone = tutor.getPhone();
        this.city = tutor.getCity();
        this.about = tutor.getAbout();
    }
}
