package br.com.leonardosbarbosa.adopet.dto.response;

import br.com.leonardosbarbosa.adopet.entities.Tutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTutorResponse {
    private Long id;
    private String fullName;
    private String email;

    public CreateTutorResponse(Tutor tutorEntity) {
        this.id = tutorEntity.getId();
        this.fullName = tutorEntity.getFullName();
        this.email = tutorEntity.getEmail();
    }
}
