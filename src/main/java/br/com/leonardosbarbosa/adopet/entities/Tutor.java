package br.com.leonardosbarbosa.adopet.entities;

import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_tutor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tutor extends User {

    @Column(nullable = false)
    private String fullName;
    private String phone;
    private String city;
    private String about;
    private String profilePic;
    @OneToMany(mappedBy = "tutor")
    private final List<Adoption> adoptions = new ArrayList<>();

    public Tutor(TutorDTO tutor) {
        this.setId(tutor.getId());
        this.fullName = tutor.getFullName();
        this.setEmail(tutor.getEmail());
        this.setPassword(tutor.getPassword());
        this.phone = tutor.getPhone();
        this.city = tutor.getCity();
        this.about = tutor.getAbout();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
