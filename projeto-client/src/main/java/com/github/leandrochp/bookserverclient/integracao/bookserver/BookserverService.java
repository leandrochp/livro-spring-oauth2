package com.github.leandrochp.bookserverclient.integracao.bookserver;

import com.github.leandrochp.bookserverclient.integracao.model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookserverService {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    public List<Livro> livros(String token) throws UsuarioSemAutorizacaoException {
        String endpoint = "http://localhost:8080/api/v2/livros";

        try {
            Livro[] livros = restTemplate.getForObject(endpoint, Livro[].class);
            return listaFromArray(livros);
        } catch (HttpClientErrorException e) {
            throw new UsuarioSemAutorizacaoException("não foi possível obter os livros do usuário");
        }
    }

    private List<Livro> listaFromArray(Livro[] livros) {
        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            lista.add(livro);
        }

        return lista;
    }
}
