package com.exemplo.autor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class AutorDTO {

    private Integer id;

    private String nome;

    private String paisOrigem;

    private Date dataNascimento;

    private List<LivroDTO> livros;

}
