package br.com.leonardosbarbosa.adopet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTutorRequest {
    @NotBlank(message = "should not be blank")
    @Size(min = 5, message = "should have min 5 characters")
    private String fullName;
    @NotBlank(message = "should not be blank")
    @Email(message = "Should be a valid email")
    private String email;
    @NotBlank(message = "should not be blank")
    @Size(min = 8, message = "should have min 8 characters")
    private String password;
}
