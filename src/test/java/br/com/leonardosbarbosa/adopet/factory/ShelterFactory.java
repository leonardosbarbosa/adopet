package br.com.leonardosbarbosa.adopet.factory;

import br.com.leonardosbarbosa.adopet.dto.request.CreateShelterRequest;
import br.com.leonardosbarbosa.adopet.dto.request.UpdateShelterRequest;
import br.com.leonardosbarbosa.adopet.entities.Shelter;

public class ShelterFactory {

    public static Shelter createShelterWithId() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        shelter.setName("Abrigo Auau");
        shelter.setEmail("auau@email.com");
        shelter.setLocation("Niterói - RJ");

        return shelter;
    }

    public static CreateShelterRequest createShelterRequest() {
        CreateShelterRequest shelterRequest = new CreateShelterRequest();
        shelterRequest.setName("New Shelter");
        shelterRequest.setEmail("shelter@email.com");
        shelterRequest.setPassword("123456");
        shelterRequest.setLocation("São Paulo - SP");

        return shelterRequest;
    }

    public static UpdateShelterRequest createUpdateShelterRequest() {
        UpdateShelterRequest shelterRequest = new UpdateShelterRequest();
        shelterRequest.setName("Abrigo Miau");
        return shelterRequest;
    }
}
