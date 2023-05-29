package br.com.leonardosbarbosa.adopet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static br.com.leonardosbarbosa.adopet.dto.validations.TutorValidationMessages.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTutorRequest {
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min = 5, message = FULL_NAME_SIZE_MESSAGE, max = 150)
    private String fullName;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Email(message = VALID_EMAIL_MESSAGE)
    @Size(max = 50)
    private String email;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min = 8, message = PASSWORD_SIZE_MESSAGE)
    private String password;
}
