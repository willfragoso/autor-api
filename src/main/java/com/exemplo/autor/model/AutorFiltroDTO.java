package com.exemplo.autor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AutorFiltroDTO {

    private Integer id;

    private String nome;

    private String paisOrigem;

    private Date dataNascimento;

}
