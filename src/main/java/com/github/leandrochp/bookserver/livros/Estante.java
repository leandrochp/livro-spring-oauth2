package com.github.leandrochp.bookserver.livros;

import com.github.leandrochp.bookserver.usuarios.Usuario;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class Estante {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Usuario usuario;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "estante_id")
    private List<Livro> livros = new ArrayList<>();

    public boolean temLivros() {
        return livros.size() > 0;
    }

    public Collection<Livro> todosLivros() {
        return Collections.unmodifiableCollection(livros);
    }

    public void adicionar(Livro livro) {
        livros.add(livro);
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
