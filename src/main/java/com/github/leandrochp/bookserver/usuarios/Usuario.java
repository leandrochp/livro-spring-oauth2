package com.github.leandrochp.bookserver.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.leandrochp.bookserver.livros.Estante;
import lombok.Getter;

import javax.persistence.*;

@Entity
public class Usuario {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String nome;

    @Getter
    @JsonIgnore
    private Credenciais credenciais;

    @Getter
    @OneToOne(mappedBy = "usuario", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Estante estante = new Estante();

    @Deprecated
    Usuario() {
    }

    public Usuario(String nome, Credenciais credenciais) {
        super();
        this.nome = nome;
        this.credenciais = credenciais;

        estante.setUsuario(this);
    }
}
