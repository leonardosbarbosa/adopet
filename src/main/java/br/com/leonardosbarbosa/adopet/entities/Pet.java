package br.com.leonardosbarbosa.adopet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_pet")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String location;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
}
