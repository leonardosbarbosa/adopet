package br.com.leonardosbarbosa.adopet.dto.request;

import br.com.leonardosbarbosa.adopet.dto.RoleDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static br.com.leonardosbarbosa.adopet.dto.validations.TutorValidationMessages.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequest {

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Email(message = VALID_EMAIL_MESSAGE)
    @Size(max = 50)
    private String email;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min = 8, message = PASSWORD_SIZE_MESSAGE)
    private String password;

    private Set<RoleDTO> roles = new HashSet<>();
}
