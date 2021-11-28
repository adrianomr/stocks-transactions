package br.com.adrianorodrigues.stockstransactions.interceptor.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TokenDto {

    private long userId;

}
