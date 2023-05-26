package br.com.leonardosbarbosa.adopet.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tb_adoption")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false, unique = true)
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;
    @Column(nullable = false)
    private Instant date;
}
