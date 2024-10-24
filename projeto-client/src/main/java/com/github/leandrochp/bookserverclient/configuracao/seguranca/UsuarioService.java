package com.github.leandrochp.bookserverclient.configuracao.seguranca;

import com.github.leandrochp.bookserverclient.usuarios.Usuario;
import com.github.leandrochp.bookserverclient.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuariosRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByLogin(username);

        return new UsuarioLogado(
                usuario.orElseThrow(
                        () -> new UsernameNotFoundException("credenciais inválidas")));

    }

}
