package br.com.leonardosbarbosa.adopet.dto;

import br.com.leonardosbarbosa.adopet.entities.Tutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import static br.com.leonardosbarbosa.adopet.dto.validations.TutorValidationMessages.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorDTO {

    private Long id;
    @Size(min = 5, max = 150, message = FULL_NAME_SIZE_MESSAGE)
    private String fullName;
    @Size(max = 50)
    @Email(message = VALID_EMAIL_MESSAGE)
    private String email;
    @Size(min = 8, message = PASSWORD_SIZE_MESSAGE)
    private String password;
    @Size(min = 11, max = 11, message = PHONE_SIZE_MESSAGE)
    private String phone;
    @Size(max = 100, message = CITY_SIZE_MESSAGE)
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
