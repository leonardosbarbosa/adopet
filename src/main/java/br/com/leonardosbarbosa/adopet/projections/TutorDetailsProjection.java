package br.com.leonardosbarbosa.adopet.projections;

public interface TutorDetailsProjection extends UserDetailsProjection {

    String getFullName();

    String getPhone();

    String getCity();

    String getAbout();

    String getProfilePic();
}
