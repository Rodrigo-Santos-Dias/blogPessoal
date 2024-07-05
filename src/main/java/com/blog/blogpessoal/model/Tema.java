package com.blog.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tb_temas")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O atributo descrição é obrigatório")
    @Size(min = 05, max = 255 ,message = "O atributo descrição deve ter entre 05 e 255 caracteres ")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "tema",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("tema")
    private List<Postagem>postagem;

    public @NotNull(message = "O atributo descrição é obrigatório") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull(message = "O atributo descrição é obrigatório") String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Postagem> getPostagem() {
        return postagem;
    }

    public void setPostagem(List<Postagem> postagem) {
        this.postagem = postagem;
    }
}
