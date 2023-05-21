package br.com.leonardosbarbosa.adopet.config.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardError {
    private Instant timestamp;
    private String error;
    private String message;
    private String path;
}
