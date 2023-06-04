package br.com.leonardosbarbosa.adopet.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.leonardosbarbosa.adopet.dto.validations.ShelterValidationMessages.LOCATION_SIZE_MESSAGE;
import static br.com.leonardosbarbosa.adopet.dto.validations.ShelterValidationMessages.NAME_SIZE_MESSAGE;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateShelterRequest {

    @Size(min = 3, max = 100, message = NAME_SIZE_MESSAGE)
    private String name;

    @Size(min = 5, max = 200, message = LOCATION_SIZE_MESSAGE)
    private String location;
}
