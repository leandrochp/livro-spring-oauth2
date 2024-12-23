package com.github.leandrochp.bookserver.configuracao.seguranca;

import com.github.leandrochp.bookserver.usuarios.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResourceOwner implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public ResourceOwner(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USUARIO_COMUM"));
        roles.add(new SimpleGrantedAuthority("read"));

        return roles;
    }

    public Integer getId() {
        return usuario.getId();
    }

    @Override
    public String getPassword() {
        return usuario.getCredenciais().getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getCredenciais().getEmail();
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

    public String getName() {
        return usuario.getNome();
    }
}
