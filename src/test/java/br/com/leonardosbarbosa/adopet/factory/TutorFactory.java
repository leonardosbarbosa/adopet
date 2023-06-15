package br.com.leonardosbarbosa.adopet.factory;

import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.dto.request.CreateTutorRequest;
import br.com.leonardosbarbosa.adopet.entities.Tutor;

public class TutorFactory {

    public static Tutor createTutorWithId() {
        Tutor tutor = Tutor.builder()
                .fullName("Leonardo Barbosa")
                .about("Desenvolvedor de Software")
                .phone("11945154979")
                .city("São Paulo")
                .build();

        tutor.setId(1L);
        return tutor;
    }

    public static CreateTutorRequest createTutorRequest() {
        CreateTutorRequest tutor = new CreateTutorRequest();
        tutor.setFullName("Leonardo Barbosa");
        tutor.setEmail("leo@email.com");
        tutor.setPassword("123456");
        return tutor;
    }

    public static TutorDTO createTutorDTO() {
        TutorDTO tutor = new TutorDTO();
        tutor.setAbout("Amante de animais!");
        tutor.setCity("Florianópolis - SC");
        return tutor;
    }
}