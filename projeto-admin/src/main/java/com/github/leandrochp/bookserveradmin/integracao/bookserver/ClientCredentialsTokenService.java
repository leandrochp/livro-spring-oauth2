package com.github.leandrochp.bookserveradmin.integracao.bookserver;

import com.github.leandrochp.bookserveradmin.integracao.BasicAuthentication;
import com.github.leandrochp.bookserveradmin.integracao.OAuth2Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@Service
public class ClientCredentialsTokenService {

    public OAuth2Token getToken() {

        RestTemplate restTemplate = new RestTemplate();
        BasicAuthentication clientAuthentication = new BasicAuthentication("cliente-admin", "123abc");

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                getBody(),
                getHeader(clientAuthentication),
                HttpMethod.POST,
                URI.create("http://localhost:8080/oauth/token")
        );

        ResponseEntity<OAuth2Token> responseEntity = restTemplate.exchange(
                requestEntity,
                OAuth2Token.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        // isso deve ser tratado de forma melhor (apenas para exemplo)
        throw new RuntimeException("error trying to retrieve access token");
    }

    private MultiValueMap<String, String> getBody() {
        MultiValueMap<String, String> dadosFormulario = new LinkedMultiValueMap<>();

        // configurar os parametros para solicitar o token
        dadosFormulario.add("grant_type", "client_credentials");
        dadosFormulario.add("scope", "read");

        return dadosFormulario;
    }

    private HttpHeaders getHeader(BasicAuthentication clientAuthentication) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Basic " + clientAuthentication.getCredenciaisBase64());

        return httpHeaders;
    }
}
