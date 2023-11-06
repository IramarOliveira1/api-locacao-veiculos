package br.fvc.api.dtos.generic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponseDTO {

    private String message;
    private Boolean error;

    public GenericResponseDTO(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }
}
