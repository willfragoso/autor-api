package com.exemplo.autor.repository;

import com.exemplo.autor.entity.Autor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {

    @Query("SELECT autor FROM Autor AS autor " +
            "WHERE ( 1 = 1 ) " +
            "AND ( autor.id = :id OR :id IS NULL ) " +
            "AND ( autor.nome LIKE :nome OR :nome IS NULL ) " +
            "AND ( autor.pseudonimo LIKE :paisOrigem OR :paisOrigem IS NULL ) " +
            "AND ( autor.dataNascimento = :dataNascimento OR :dataNascimento IS NULL ) ")
    Page<Autor> pesquisarAutores(@Param("id") Integer id,
                                 @Param("nome") String nome,
                                 @Param("paisOrigem") String paisOrigem,
                                 @Param("dataNascimento") Date dataNascimento,
                                 Pageable pageable);

}
