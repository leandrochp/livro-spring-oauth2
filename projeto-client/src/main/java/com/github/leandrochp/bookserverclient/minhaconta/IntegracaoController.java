package com.github.leandrochp.bookserverclient.minhaconta;

import com.github.leandrochp.bookserverclient.configuracao.seguranca.UsuarioLogado;
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
    private PasswordTokenService passwordTokenService;

    @Autowired
    private UsuariosRepository usuarios;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView integracao() {
        return new ModelAndView("minhaconta/integracao");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView autorizar(Autorizacao autorizacao) {
        Usuario usuario = usuarioLogado();

        OAuth2Token token = passwordTokenService.getToken(autorizacao.getLogin(), autorizacao.getSenha());

        AcessoBookserver acessoBookserver = new AcessoBookserver();
        acessoBookserver.setAccessToken(token.getAccessToken());

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
