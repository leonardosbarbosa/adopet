package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.dto.TutorDTO;
import br.com.leonardosbarbosa.adopet.entities.Tutor;
import br.com.leonardosbarbosa.adopet.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public Page<TutorDTO> findAll(Pageable pageRequest) {
        Page<Tutor> tutorsPage = tutorRepository.findAll(pageRequest);

        return tutorsPage.map(TutorDTO::new);
    }

    public TutorDTO createNew(TutorDTO tutor) {
        Tutor tutorEntity = new Tutor(tutor);
        tutorEntity = tutorRepository.save(tutorEntity);
        return new TutorDTO(tutorEntity);
    }

    public TutorDTO update(Long id, TutorDTO dto) {
        Tutor tutorEntity = tutorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        copyDtoToEntity(dto, tutorEntity);
        tutorEntity = tutorRepository.save(tutorEntity);
        return new TutorDTO(tutorEntity);
    }

    public void delete(Long id) {
        tutorRepository.deleteById(id);
    }

    private void copyDtoToEntity(TutorDTO dto, Tutor entity) {

        if (dto.getFullName() != null) {
            entity.setFullName(dto.getFullName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getCity() != null) {
            entity.setCity(dto.getCity());
        }

        if (dto.getAbout() != null) {
            entity.setAbout(dto.getAbout());
        }
    }
}
