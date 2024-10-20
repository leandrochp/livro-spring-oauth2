package com.github.leandrochp.bookserver.usuarios;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
public class Credenciais {

    @Getter
    private String email;

    @Getter
    private String senha;

    @Deprecated
    Credenciais() {
    }

    public Credenciais(String email, String senha) {
        super();
        this.email = email;
        this.senha = senha;
    }
}
