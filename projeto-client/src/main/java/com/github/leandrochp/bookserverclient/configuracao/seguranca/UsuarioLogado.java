package com.github.leandrochp.bookserverclient.configuracao.seguranca;

import com.github.leandrochp.bookserverclient.usuarios.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioLogado implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public UsuarioLogado(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return usuario.getNome();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return usuario.getId();
    }
}
