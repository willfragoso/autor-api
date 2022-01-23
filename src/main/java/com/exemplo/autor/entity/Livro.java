package com.exemplo.autor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "SCHEMA_AUTOR", name = "LIVRO")
public class Livro {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AUTOR")
    @ToString.Exclude
    private Autor autor;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "NUMERO_PAGINAS")
    private Integer numeroPaginas;

    @Column(name = "DATA_PUBLICACAO")
    private Date dataPublicacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Livro livro = (Livro) o;
        return id != null && Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
