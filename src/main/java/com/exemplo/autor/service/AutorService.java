package com.exemplo.autor.service;

import com.exemplo.autor.entity.Autor;
import com.exemplo.autor.entity.Livro;
import com.exemplo.autor.exception.BusinessException;
import com.exemplo.autor.exception.InvalidFieldSizeException;
import com.exemplo.autor.model.AutorDTO;
import com.exemplo.autor.model.AutorFiltroDTO;
import com.exemplo.autor.model.LivroDTO;
import com.exemplo.autor.repository.AutorRepository;
import com.exemplo.autor.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AutorService {

    private final AutorRepository autorRepository;

    private final LivroRepository livroRepository;

    public Page<AutorDTO> pesquisarAutores(@RequestParam AutorFiltroDTO autorFiltroDTO,
                                           @PageableDefault(size = 5) Pageable pageable) {
        Page<Autor> autorPage = autorRepository.pesquisarAutores(
                autorFiltroDTO.getId(),
                StringUtils.isAllBlank(autorFiltroDTO.getNome()) ? autorFiltroDTO.getNome() : "%" + autorFiltroDTO.getNome() + "%",
                StringUtils.isAllBlank(autorFiltroDTO.getPseudonimo()) ? autorFiltroDTO.getPseudonimo() : "%" + autorFiltroDTO.getPseudonimo() + "%",
                autorFiltroDTO.getDataNascimento(),
                pageable);
        return autorPage.map(
                autor -> AutorDTO.builder()
                        .id(autor.getId())
                        .nome(autor.getNome())
                        .pseudonimo(autor.getPseudonimo())
                        .dataNascimento(autor.getDataNascimento())
                        .build()
        );
    }

    public AutorDTO carregarAutor(Integer idAutor) {
        Autor autor = autorRepository.carregarAutorPorId(idAutor);
        List<Livro> listaLivro = livroRepository.recuperarLivrosDoAutor(idAutor);
        return montarAutorDTO(idAutor, autor, listaLivro);
    }

    private AutorDTO montarAutorDTO(Integer idAutor, Autor autor, List<Livro> listaLivro) {
        return AutorDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .pseudonimo(autor.getPseudonimo())
                .dataNascimento(autor.getDataNascimento())
                .livros(montarListaLivroDTO(idAutor, listaLivro))
                .build();
    }

    private List<LivroDTO> montarListaLivroDTO(Integer idAutor, List<Livro> listaLivro) {
        List<LivroDTO> listaLivroDTO = new ArrayList<>();
        for (Livro livro : listaLivro) {
            listaLivroDTO.add(
                    LivroDTO.builder()
                            .id(livro.getId())
                            .idAutor(idAutor)
                            .nome(livro.getNome())
                            .numeroPaginas(livro.getNumeroPaginas())
                            .dataPublicacao(livro.getDataPublicacao())
                            .build()
            );
        }
        return listaLivroDTO;
    }

    @Transactional
    public AutorDTO incluirAutor(AutorDTO autorDTO) {
        validarTamanhoCampos(autorDTO);
        validarPreenchimentoCamposObrigatorios(autorDTO, Boolean.FALSE);
        Autor autor = Autor.builder()
                .id(null)
                .nome(autorDTO.getNome())
                .pseudonimo(autorDTO.getPseudonimo())
                .dataNascimento(autorDTO.getDataNascimento())
                .build();
        autorRepository.save(autor);
        if (autorDTO.getLivros() != null) {
            for (LivroDTO livroDTO : autorDTO.getLivros()) {
                aplicarInclusaoNovoLivro(autor, livroDTO);
            }
        }
        autorDTO.setId(autor.getId());
        return autorDTO;
    }

    private void validarTamanhoCampos(AutorDTO autorDTO) {
        if (autorDTO != null) {
            if (!StringUtils.isAllBlank(autorDTO.getNome()) && autorDTO.getNome().length() > 50) {
                throw new InvalidFieldSizeException("O nome do autor deve ter no m??ximo 50 caracteres.");
            }
            if (!StringUtils.isAllBlank(autorDTO.getPseudonimo()) && autorDTO.getPseudonimo().length() > 50) {
                throw new InvalidFieldSizeException("O pseud??nimo do autor  deve ter no m??ximo 50 caracteres.");
            }
            if (autorDTO.getLivros() != null) {
                for (LivroDTO livroDTO : autorDTO.getLivros()) {
                    if (!StringUtils.isAllBlank(livroDTO.getNome()) && livroDTO.getNome().length() > 50) {
                        throw new InvalidFieldSizeException("O nome do livro deve ter no m??ximo 50 caracteres.");
                    }
                }
            }
        }
    }

    private void validarPreenchimentoCamposObrigatorios(AutorDTO autorDTO, boolean isAlteracao) {
        if (isAlteracao) {
            if (autorDTO.getId() == null) {
                throw new BusinessException("?? obrigat??rio informar o identificador do autor.");
            }
        }
        if (StringUtils.isAllBlank(autorDTO.getNome())) {
            throw new BusinessException("?? obrigat??rio informar o nome do autor.");
        }
        if (StringUtils.isAllBlank(autorDTO.getPseudonimo())) {
            throw new BusinessException("?? obrigat??rio informar o pseud??nimo do autor.");
        }
        if (autorDTO.getDataNascimento() == null) {
            throw new BusinessException("?? obrigat??rio informar a data de nascimento do autor.");
        }
        if (autorDTO.getLivros() != null) {
            for (LivroDTO livroDTO : autorDTO.getLivros()) {
                if (StringUtils.isAllBlank(livroDTO.getNome())) {
                    throw new BusinessException("?? obrigat??rio informar o nome do livro.");
                }
                if (livroDTO.getNumeroPaginas() == null) {
                    throw new BusinessException("?? obrigat??rio informar o n??mero de p??ginas do livro.");
                }
                if (livroDTO.getDataPublicacao() == null) {
                    throw new BusinessException("?? obrigat??rio informar a data de publica????o do livro.");
                }
            }
        }
    }

    @Transactional
    public AutorDTO alterarAutor(AutorDTO autorDTO) {
        Optional<Autor> optionalAutor = autorRepository.findById(autorDTO.getId());
        if (optionalAutor.isPresent()) {
            validarTamanhoCampos(autorDTO);
            validarPreenchimentoCamposObrigatorios(autorDTO, Boolean.TRUE);
            validarVinculosAutorLivro(autorDTO);
            Autor autor = optionalAutor.get();
            autor.setNome(autorDTO.getNome());
            autor.setPseudonimo(autorDTO.getPseudonimo());
            autor.setDataNascimento(autorDTO.getDataNascimento());
            autorRepository.save(autor);
            List<Livro> livros = livroRepository.recuperarLivrosDoAutor(autor.getId());
            aplicarAlteracoesLivros(autor, livros, autorDTO);
            autorDTO.setId(autor.getId());
        } else {
            String message = String.format("Nenhum autor com o identificador %d foi encontrado.", autorDTO.getId());
            throw new BusinessException(message);
        }
        return autorDTO;
    }

    private void validarVinculosAutorLivro(AutorDTO autorDTO) {
        if (autorDTO.getLivros() != null) {
            for (LivroDTO livroDTO : autorDTO.getLivros()) {
                if (livroDTO.getIdAutor() != null
                        && !autorDTO.getId().equals(livroDTO.getIdAutor())) {
                    throw new BusinessException("S?? ?? permitido alterar os livros do mesmo autor. Por favor, verifique os v??nculos dos cadastros.");
                }
            }
        }
    }

    private void aplicarAlteracoesLivros(Autor autor, List<Livro> livros, AutorDTO autorDTO) {
        if (autorDTO.getLivros() == null) {
            livroRepository.deletarTodosLivrosDoAutor(autor.getId());
        } else {
            for (LivroDTO livroDTO : autorDTO.getLivros()) {
                if (livroDTO.getId() == null) {
                    aplicarInclusaoNovoLivro(autor, livroDTO);
                } else {
                    aplicarAlteracaoLivroCadastradoPreviamente(autor.getId(), livroDTO);
                }
            }
            aplicarAlteracaoLivrosDeletados(livros, autorDTO.getLivros());
        }
    }

    private void aplicarInclusaoNovoLivro(Autor autor, LivroDTO livroDTO) {
        Livro livro = Livro.builder()
                .id(null)
                .autor(autor)
                .nome(livroDTO.getNome())
                .numeroPaginas(livroDTO.getNumeroPaginas())
                .dataPublicacao(livroDTO.getDataPublicacao())
                .build();
        livroRepository.save(livro);
    }

    private void aplicarAlteracaoLivroCadastradoPreviamente(Integer idAutor, LivroDTO livroDTO) {
        Optional<Livro> optionalLivro = livroRepository.carregarLivroPorId(livroDTO.getId());
        if (optionalLivro.isPresent()) {
            if (!idAutor.equals(optionalLivro.get().getAutor().getId())) {
                throw new BusinessException("S?? ?? permitido alterar os livros do mesmo autor. Por favor, verifique os v??nculos dos cadastros.");
            }
            Livro livro = optionalLivro.get();
            livro.setNome(livroDTO.getNome());
            livro.setNumeroPaginas(livroDTO.getNumeroPaginas());
            livro.setDataPublicacao(livroDTO.getDataPublicacao());
            livroRepository.save(livro);
        } else {
            String message = String.format("Nenhum livro com o identificador %d foi encontrado.", livroDTO.getId());
            throw new BusinessException(message);
        }
    }

    private void aplicarAlteracaoLivrosDeletados(List<Livro> livros, List<LivroDTO> listaLivroDTO) {
        Iterator<Livro> iterator = livros.iterator();
        while (iterator.hasNext()) {
            Livro livro = iterator.next();
            boolean livroFoiDeletado = Boolean.TRUE;
            for (LivroDTO livroDTO : listaLivroDTO) {
                if (livroDTO.getId() != null && livroDTO.getId().equals(livro.getId())) {
                    livroFoiDeletado = Boolean.FALSE;
                    break;
                }
            }
            if (livroFoiDeletado) {
                livroRepository.delete(livro);
                iterator.remove();
            }
        }
    }

    @Transactional
    public void deletarAutor(Integer id) {
        Optional<Autor> optionalAutor = autorRepository.findById(id);
        if (optionalAutor.isPresent()) {
            livroRepository.deletarTodosLivrosDoAutor(id);
            autorRepository.delete(optionalAutor.get());
        } else {
            String message = String.format("Nenhum autor com o identificador %d foi encontrado.", id);
            throw new BusinessException(message);
        }
    }

    public long recuperarQtdeTotalRegistros() {
        return autorRepository.count();
    }

}
