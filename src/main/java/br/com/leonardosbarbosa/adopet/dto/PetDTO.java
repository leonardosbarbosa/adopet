package br.com.leonardosbarbosa.adopet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class PetDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Integer age;
    @NotBlank
    private String location;
    @NotNull
    private Long shelterId;
}
