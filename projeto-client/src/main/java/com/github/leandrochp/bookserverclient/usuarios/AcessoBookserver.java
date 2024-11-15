package com.github.leandrochp.bookserverclient.usuarios;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Calendar;

@Embeddable
@ToString
public class AcessoBookserver {

    @Getter
    @Setter
    @Column(name = "login_bookserver")
    private String login;

    @Getter
    @Setter
    @Column(name = "senha_bookserver")
    private String senha;

    @Getter
    @Setter
    @Column(name = "token_bookserver")
    private String accessToken;

    @Getter
    @Setter
    @Column(name = "expiracao_token")
    private Calendar dataDeExpiracao;

}
