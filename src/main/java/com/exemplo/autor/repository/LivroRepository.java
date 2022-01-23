package com.exemplo.autor.repository;

import com.exemplo.autor.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Modifying
    @Query("DELETE FROM Livro AS livro WHERE livro.autor.id = :idAutor ")
    void deletarTodosLivrosDoAutor(@Param("idAutor") Integer idAutor);

    @Query("SELECT livro FROM Livro AS livro WHERE livro.autor.id = :idAutor ")
    List<Livro> recuperarLivrosDoAutor(@Param("idAutor") Integer idAutor);

    @Query("SELECT livro FROM Livro AS livro " +
            "LEFT JOIN FETCH livro.autor autor " +
            "WHERE livro.id = :id ")
    Optional<Livro> carregarLivroPorId(@Param("id") Integer id);

}
