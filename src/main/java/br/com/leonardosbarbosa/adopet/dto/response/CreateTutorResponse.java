package br.com.leonardosbarbosa.adopet.dto.response;

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
}
