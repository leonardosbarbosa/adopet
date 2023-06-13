package br.com.leonardosbarbosa.adopet.factory;

import br.com.leonardosbarbosa.adopet.entities.Tutor;

public class TutorFactory {

    public static Tutor createTutor() {
        Tutor tutor = Tutor.builder()
                .fullName("Leonardo Barbosa")
                .about("Desenvolvedor de Software")
                .phone("11945154979")
                .city("São Paulo")
                .build();

        tutor.setId(1L);
        return tutor;
    }
}
