package br.com.leonardosbarbosa.adopet.repositories;

import br.com.leonardosbarbosa.adopet.entities.Tutor;
import br.com.leonardosbarbosa.adopet.projections.TutorDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query(nativeQuery = true, value = """
            SELECT t.id, t.full_name as fullName, t.phone, t.city, t.about, t.profile_pic as profilePic,
            u.email AS username, u.password, r.id AS roleId, r.authority
            			FROM tb_user u
            			INNER JOIN tb_user_role ur ON u.id = ur.user_id
            			INNER JOIN tb_role r ON r.id = ur.role_id
            			INNER JOIN tb_tutor t ON t.id = u.id
            			WHERE u.email = :email
            """)
    List<TutorDetailsProjection> searchUserAndRolesByEmail(String email);
}
