package br.iff.edu.ccc.clickagenda.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private String error;
    private long timestamp;
}
