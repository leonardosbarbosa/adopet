package br.com.leonardosbarbosa.adopet.dto;

import br.com.leonardosbarbosa.adopet.entities.Tutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorDTO {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String about;

    public TutorDTO(Tutor tutor) {
        this.id = tutor.getId();
        this.fullName = tutor.getFullName();
        this.email = tutor.getEmail();
        this.password = tutor.getPassword();
        this.phone = tutor.getPhone();
        this.city = tutor.getCity();
        this.about = tutor.getAbout();
    }
}
