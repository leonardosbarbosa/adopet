package br.com.leonardosbarbosa.adopet.services.enums;

public enum RolesEnum {
    TUTOR(1L),
    SHELTER(2L);

    public final Long code;

    RolesEnum(Long code) {
        this.code = code;
    }
}
