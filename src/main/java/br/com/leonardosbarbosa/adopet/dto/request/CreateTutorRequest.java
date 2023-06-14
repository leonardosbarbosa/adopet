package br.com.leonardosbarbosa.adopet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import static br.com.leonardosbarbosa.adopet.dto.validations.TutorValidationMessages.FULL_NAME_SIZE_MESSAGE;
import static br.com.leonardosbarbosa.adopet.dto.validations.TutorValidationMessages.NOT_BLANK_MESSAGE;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTutorRequest extends CreateUserRequest {
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min = 5, message = FULL_NAME_SIZE_MESSAGE, max = 150)
    private String fullName;
}
