package com.github.leandrochp.bookserverclient.minhaconta;

import com.github.leandrochp.bookserverclient.configuracao.seguranca.UsuarioLogado;
import com.github.leandrochp.bookserverclient.integracao.bookserver.AuthorizationCodeTokenService;
import com.github.leandrochp.bookserverclient.integracao.bookserver.ImplicitTokenService;
import com.github.leandrochp.bookserverclient.integracao.bookserver.OAuth2Token;
import com.github.leandrochp.bookserverclient.integracao.bookserver.PasswordTokenService;
import com.github.leandrochp.bookserverclient.usuarios.AcessoBookserver;
import com.github.leandrochp.bookserverclient.usuarios.Usuario;
import com.github.leandrochp.bookserverclient.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/integracao")
public class IntegracaoController {

    @Autowired
    private UsuariosRepository usuarios;

    @Autowired
    private PasswordTokenService passwordTokenService;

    @Autowired
    private AuthorizationCodeTokenService authorizationCodeTokenService;

    @Autowired
    private ImplicitTokenService implicitTokenService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView integracao() {
        // grant type Implicit
        //String endpointDeAutorizacao = implicitTokenService.getAuthorizationEndpoint();
        //return  new ModelAndView("redirect:" + endpointDeAutorizacao);

        // grant type Authorization Code
        String endpointDeAutorizacao = authorizationCodeTokenService.getAuthorizationEndpoint();
        return new ModelAndView("redirect:" + endpointDeAutorizacao);

        // grant type Resource Source Password Credentials
        //return new ModelAndView("/minhaconta/integracao");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/implicit")
    public ModelAndView implicit() {
        return new ModelAndView("/minhaconta/bookserver");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/callback")
    public ModelAndView callback(String code, String state) {
        return new ModelAndView("forward:/minhaconta/principal");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView autorizar(Autorizacao autorizacao) {
        OAuth2Token token = passwordTokenService.getToken(autorizacao.getLogin(), autorizacao.getSenha());

        AcessoBookserver acessoBookserver = new AcessoBookserver();
        acessoBookserver.setAccessToken(token.getAccessToken());

        Usuario usuario = usuarioLogado();
        usuario.setAcessoBookserver(acessoBookserver);
        usuarios.save(usuario);

        return new ModelAndView("redirect:/minhaconta/principal");
    }

    private Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        return usuarios.findById(usuarioLogado.getId());
    }
}
