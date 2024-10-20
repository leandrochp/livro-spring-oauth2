package com.github.leandrochp.bookserver.livros;


import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Livro {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String titulo;

    @Getter
    @Range(min = 0, max = 10)
    private int nota;

    @Deprecated
    Livro() {
    }

    public Livro(String titulo, int nota) {
        super();
        this.titulo = titulo;
        this.nota = nota;
    }
}
