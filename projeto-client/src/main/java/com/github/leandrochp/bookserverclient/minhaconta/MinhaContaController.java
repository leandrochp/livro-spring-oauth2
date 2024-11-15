package com.github.leandrochp.bookserverclient.minhaconta;

import com.github.leandrochp.bookserverclient.configuracao.seguranca.UsuarioLogado;
import com.github.leandrochp.bookserverclient.integracao.bookserver.BookserverService;
import com.github.leandrochp.bookserverclient.integracao.bookserver.UsuarioSemAutorizacaoException;
import com.github.leandrochp.bookserverclient.usuarios.Usuario;
import com.github.leandrochp.bookserverclient.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/minhaconta")
public class MinhaContaController {

    @Autowired
    private BookserverService bookserverService;

    @Autowired
    private UsuariosRepository usuarios;

    @RequestMapping(value = "/principal")
    public ModelAndView principal() {
        Usuario usuario = usuarioLogado();
        String token = usuario.getAcessoBookserver().getAccessToken();

        ModelAndView mv = new ModelAndView("minhaconta/principal");

        try {
            mv.addObject("livros", bookserverService.livros(token));
        } catch (UsuarioSemAutorizacaoException e) {
            mv.addObject("erro", e.getMessage());
        }

        return mv;
    }

    private Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        return usuarios.findById(usuarioLogado.getId());
    }

}
