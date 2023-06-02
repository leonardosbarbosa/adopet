package br.com.leonardosbarbosa.adopet.repositories;

import br.com.leonardosbarbosa.adopet.entities.Shelter;
import br.com.leonardosbarbosa.adopet.projections.ShelterDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    @Query(nativeQuery = true, value = """
            SELECT s.id, t.full_name as fullName, t.phone, t.city, t.about, t.profile_pic as profilePic,
            u.email AS username, u.password, r.id AS roleId, r.authority
            			FROM tb_user u
            			INNER JOIN tb_user_role ur ON u.id = ur.user_id
            			INNER JOIN tb_role r ON r.id = ur.role_id
            			INNER JOIN tb_tutor t ON t.id = u.id
            			WHERE u.email = :email
            """)
    List<ShelterDetailsProjection> searchUserAndRolesByEmail(String email);
}
