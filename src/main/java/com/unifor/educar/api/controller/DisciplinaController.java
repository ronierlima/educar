package com.unifor.educar.api.controller;

import com.unifor.educar.domain.model.Disciplina;
import com.unifor.educar.domain.repository.DisciplinaRepository;
import com.unifor.educar.domain.service.DisciplinaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaRepository disciplinaRepository;
    private final DisciplinaService registroCursoService;

    @GetMapping
    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disciplina adicionar(@Valid @RequestBody Disciplina curso) {
        return registroCursoService.salvar(curso);
    }

    @GetMapping("/{disciplinaId}")
    public ResponseEntity<Disciplina> buscar(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(registroCursoService.buscar(disciplinaId));
    }

    @PutMapping("/{disciplinaId}")
    public ResponseEntity<Disciplina> atualizar(@PathVariable Long disciplinaId, @Valid @RequestBody Disciplina disciplina) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            return ResponseEntity.notFound().build();
        }

        disciplina.setId(disciplinaId);
        Disciplina disciplinaAtualizada = registroCursoService.salvar(disciplina);

        return ResponseEntity.ok(disciplinaAtualizada);
    }

    @DeleteMapping("/{disciplinaId}")
    public ResponseEntity<Void> remover(@PathVariable Long disciplinaId) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            return ResponseEntity.notFound().build();
        }

        registroCursoService.excluir(disciplinaId);
        return ResponseEntity.noContent().build();
    }
}
