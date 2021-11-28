package br.com.adrianorodrigues.stockstransactions.interceptor.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ErrorDto {

    private int status;
    private String message;

}
