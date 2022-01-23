package com.exemplo.autor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "SCHEMA_AUTOR", name = "AUTOR")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOME", length = 50)
    private String nome;

    @Column(name = "PAIS_ORIGEM", length = 50)
    private String paisOrigem;

    @Column(name = "DATA_NASCIMENTO")
    private Date dataNascimento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Autor autor = (Autor) o;
        return id != null && Objects.equals(id, autor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
