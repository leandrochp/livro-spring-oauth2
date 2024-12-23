package com.github.leandrochp.bookserverclient.integracao.bookserver;

import com.github.leandrochp.bookserverclient.configuracao.seguranca.BasicAuthentication;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImplicitTokenService {

    public String getAuthorizationEndpoint() {
        BasicAuthentication clientAuthentication = new BasicAuthentication("cliente-app", "123456");

        String endpointDeAutorizacao = "http://localhost:8080/oauth/authorize";

        // montando a URI
        Map<String, String> parametros = new HashMap<>();
        parametros.put("response_type", "token");
        parametros.put("client_id", getEncodedUrl(clientAuthentication.getLogin()));
        parametros.put("redirect_uri", getEncodedUrl("http://localhost:9000/integracao/implicit"));
        parametros.put("scope", getEncodedUrl("read write"));

        return construirUrl(endpointDeAutorizacao, parametros);
    }

    private String construirUrl(String endpointDeAutorizacao, Map<String, String> parametros) {
        List<String> parametrosDeAutorizacao = new ArrayList<>(parametros.size());

        parametros.forEach((param, valor) -> {
            parametrosDeAutorizacao.add(param + "=" + valor);
        });

        return endpointDeAutorizacao + "?" + parametrosDeAutorizacao.stream()
                .reduce((a,b) -> a + "&" + b).get();
    }

    private String getEncodedUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
