package com.blog.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_postagem")
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O atributo título é Obrigatório!!")
    @Size(min = 05, max = 100,message = "O atributo título deve ter entre 05 e 100(cem) caracteres ")
    private String titulo;

    @NotBlank(message = "O atributo texto é Obrigatório!!")
    @Size(min = 10, max = 1000,message = "O atributo texto deve ter entre 10 e 1000(mil) caracteres ")
    private String texto;

    @UpdateTimestamp
    private LocalDateTime data;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "O atributo título é Obrigatório!!") @Size(min = 05, max = 100, message = "O atributo título deve ter entre 05 e 100(cem) caracteres ") String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NotBlank(message = "O atributo título é Obrigatório!!") @Size(min = 05, max = 100, message = "O atributo título deve ter entre 05 e 100(cem) caracteres ") String titulo) {
        this.titulo = titulo;
    }

    public @NotBlank(message = "O atributo texto é Obrigatório!!") @Size(min = 10, max = 1000, message = "O atributo texto deve ter entre 10 e 1000(mil) caracteres ") String getTexto() {
        return texto;
    }

    public void setTexto(@NotBlank(message = "O atributo texto é Obrigatório!!") @Size(min = 10, max = 1000, message = "O atributo texto deve ter entre 10 e 1000(mil) caracteres ") String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
