package com.exemplo.autor.component;

import com.exemplo.autor.util.DataUtil;
import com.exemplo.autor.model.AutorDTO;
import com.exemplo.autor.model.LivroDTO;
import com.exemplo.autor.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final AutorService autorService;

    @Autowired
    public DataLoader(AutorService autorService) {
        this.autorService = autorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long qtdeTotalRegistros = autorService.recuperarQtdeTotalRegistros();
        if (qtdeTotalRegistros == 0) {
            AutorDTO autorDTOJKRowling = gerarAutorDTOJKRowling();
            autorService.incluirAutor(autorDTOJKRowling);

            AutorDTO autorDTOJRRTolkein = gerarAutorDTOJRRTolkein();
            autorService.incluirAutor(autorDTOJRRTolkein);
        }
    }

    private AutorDTO gerarAutorDTOJKRowling() throws ParseException {
        return AutorDTO.builder()
                .id(null)
                .nome("Joanne Rowling")
                .pseudonimo("J. K. Rowling")
                .dataNascimento(DataUtil.getDateFromString("31/07/1965", "dd/MM/yyyy"))
                .livros(gerarListaLivroDTOHarryPotter())
                .build();
    }

    private List<LivroDTO> gerarListaLivroDTOHarryPotter() throws ParseException {
        List<LivroDTO> listaLivroDTO = new ArrayList<>();

        LivroDTO livroDTOHP1 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("Harry Potter e a Pedra Filosofal")
                .numeroPaginas(223)
                .dataPublicacao(DataUtil.getDateFromString("01/01/2000", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOHP2 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("Harry Potter e a Câmara Secreta")
                .numeroPaginas(275)
                .dataPublicacao(DataUtil.getDateFromString("17/01/2000", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOHP3 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("Harry Potter e o Prisioneiro de Azkaban")
                .numeroPaginas(348)
                .dataPublicacao(DataUtil.getDateFromString("01/12/2000", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOHP4 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("Harry Potter e o Cálice de Fogo")
                .numeroPaginas(590)
                .dataPublicacao(DataUtil.getDateFromString("01/06/2001", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOHP5 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("Harry Potter e a Ordem da Fênix")
                .numeroPaginas(704)
                .dataPublicacao(DataUtil.getDateFromString("29/11/2003", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOHP6 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("Harry Potter e o Príncipe Mestiço")
                .numeroPaginas(471)
                .dataPublicacao(DataUtil.getDateFromString("26/11/2005", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOHP7 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("Harry Potter e as Relíquias da Morte")
                .numeroPaginas(551)
                .dataPublicacao(DataUtil.getDateFromString("08/11/2007", "dd/MM/yyyy"))
                .build();

        listaLivroDTO.add(livroDTOHP1);
        listaLivroDTO.add(livroDTOHP2);
        listaLivroDTO.add(livroDTOHP3);
        listaLivroDTO.add(livroDTOHP4);
        listaLivroDTO.add(livroDTOHP5);
        listaLivroDTO.add(livroDTOHP6);
        listaLivroDTO.add(livroDTOHP7);

        return listaLivroDTO;
    }

    private AutorDTO gerarAutorDTOJRRTolkein() throws ParseException {
        return AutorDTO.builder()
                .id(null)
                .nome("John Ronald Reuel Tolkien")
                .pseudonimo("J. R. R. Tolkien")
                .dataNascimento(DataUtil.getDateFromString("03/01/1892", "dd/MM/yyyy"))
                .livros(gerarListaLivroDTOJRRTolkein())
                .build();
    }

    private List<LivroDTO> gerarListaLivroDTOJRRTolkein() throws ParseException {
        List<LivroDTO> listaLivroDTO = new ArrayList<>();

        LivroDTO livroDTOLOTR1 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("O Sernhor dos Anéis - A Sociedade do Anel")
                .numeroPaginas(423)
                .dataPublicacao(DataUtil.getDateFromString("29/07/1954", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOLOTR2 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("O Sernhor dos Anéis - As Duas Torres")
                .numeroPaginas(352)
                .dataPublicacao(DataUtil.getDateFromString("11/11/1954", "dd/MM/yyyy"))
                .build();

        LivroDTO livroDTOLOTR3 = LivroDTO.builder()
                .id(null)
                .idAutor(null)
                .nome("O Sernhor dos Anéis - O Retorno do Rei")
                .numeroPaginas(416)
                .dataPublicacao(DataUtil.getDateFromString("20/10/1955", "dd/MM/yyyy"))
                .build();

        listaLivroDTO.add(livroDTOLOTR1);
        listaLivroDTO.add(livroDTOLOTR2);
        listaLivroDTO.add(livroDTOLOTR3);

        return listaLivroDTO;
    }

}
