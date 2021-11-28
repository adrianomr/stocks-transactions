package br.com.adrianorodrigues.stockstransactions.interceptor.token;

import br.com.adrianorodrigues.stockstransactions.exceptions.InvalidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenContext {

    private static final InheritableThreadLocal<TokenDto> TOKEN = new InheritableThreadLocal<>();
    private final Base64.Decoder decoder = Base64.getDecoder();
    private final ObjectMapper objectMapper;

    public void fromBearerToken(String authorization) {
        try {
            String[] chunks = authorization.split("\\.");
            String payload = new String(decoder.decode(chunks[1]));
            TokenDto tokenDto = objectMapper.readValue(payload, TokenDto.class);
            TOKEN.set(tokenDto);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    public TokenDto getTokenDto(){
        return TOKEN.get();
    }
}
