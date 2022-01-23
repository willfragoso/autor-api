package com.exemplo.autor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class LivroDTO {

    private Integer id;

    private Integer idAutor;

    private String nome;

    private Integer numeroPaginas;

    private Date dataPublicacao;

}
