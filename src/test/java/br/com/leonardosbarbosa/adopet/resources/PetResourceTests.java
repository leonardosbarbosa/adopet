package br.com.leonardosbarbosa.adopet.resources;

import br.com.leonardosbarbosa.adopet.config.errors.exceptions.ResourceNotFoundException;
import br.com.leonardosbarbosa.adopet.dto.PetDTO;
import br.com.leonardosbarbosa.adopet.factory.PetFactory;
import br.com.leonardosbarbosa.adopet.services.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PetResourceTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PetService petService;
    @Autowired
    private ObjectMapper objectMapper;

    private final Long existentId = 1L;
    private final Long nonExistentId = 2L;
    private final PetDTO petRequest = PetFactory.createPetDTORequest();
    private final PetDTO petResponse = new PetDTO(PetFactory.createPetWithId());

    @Test
    void findAllShouldReturnPage() throws Exception {
        when(petService.findAll(any())).thenReturn(new PageImpl<>(List.of(petResponse)));

        ResultActions result = mockMvc.perform(get("/pets")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnPetWhenIdExists() throws Exception {
        when(petService.findById(existentId)).thenReturn(petResponse);

        ResultActions result = mockMvc.perform(get("/pets/{id}", existentId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        when(petService.findById(nonExistentId)).thenThrow(ResourceNotFoundException.class);

        ResultActions result = mockMvc.perform(get("/pets/{id}", nonExistentId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void createNewShouldReturnPetDTOAndHttpStatusCreated() throws Exception {
        when(petService.createNew(any(PetDTO.class))).thenReturn(petResponse);
        String jsonBody = objectMapper.writeValueAsString(petRequest);

        ResultActions result = mockMvc.perform(post("/pets")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void updateShouldReturnPetDTOWhenIdExists() throws Exception {
        when(petService.updateById(eq(existentId), any(PetDTO.class))).thenReturn(petResponse);
        String jsonBody = objectMapper.writeValueAsString(petRequest);

        ResultActions result = mockMvc.perform(put("/pets/{id}", existentId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        when(petService.updateById(eq(nonExistentId), any(PetDTO.class))).thenThrow(ResourceNotFoundException.class);
        String jsonBody = objectMapper.writeValueAsString(petRequest);

        ResultActions result = mockMvc.perform(put("/pets/{id}", nonExistentId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        doNothing().when(petService).deleteById(existentId);

        ResultActions result = mockMvc.perform(delete("/pets/{id}", existentId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundIdDoesNotExist() throws Exception {
        doThrow(ResourceNotFoundException.class).when(petService).deleteById(nonExistentId);

        ResultActions result = mockMvc.perform(delete("/pets/{id}", nonExistentId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
