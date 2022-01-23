package com.exemplo.autor.controller;

import com.exemplo.autor.model.AutorDTO;
import com.exemplo.autor.model.AutorFiltroDTO;
import com.exemplo.autor.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AutorController {

    private final AutorService autorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AutorDTO> pesquisarAutores(AutorFiltroDTO autorFiltroDTO,
                                           @PageableDefault(size = 5) Pageable pageable) {
        return autorService.pesquisarAutores(autorFiltroDTO, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutorDTO incluirAutor(@RequestBody AutorDTO autorDTO) {
        return autorService.incluirAutor(autorDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AutorDTO alterarAutor(@RequestBody AutorDTO autorDTO) {
        return autorService.alterarAutor(autorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAutor(@PathVariable("id") Integer id) {
        autorService.deletarAutor(id);
    }

}
