package com.unifor.educar.api.controller;

import com.unifor.educar.domain.model.Curso;
import com.unifor.educar.domain.model.MatrizCurricular;
import com.unifor.educar.domain.repository.CursoRepository;
import com.unifor.educar.domain.service.RegistroCursoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository cursoRepository;
    private final RegistroCursoService registroCursoService;

    @GetMapping
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curso adicionar(@Valid @RequestBody Curso curso) {
        return registroCursoService.salvar(curso);
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<Curso> buscar(@PathVariable Long cursoId) {
        return ResponseEntity.ok(registroCursoService.buscar(cursoId));
    }

    @PutMapping("/{cursoId}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long cursoId, @Valid @RequestBody Curso curso) {
        if (!cursoRepository.existsById(cursoId)) {
            return ResponseEntity.notFound().build();
        }

        curso.setId(cursoId);
        Curso cursoAtualizado = registroCursoService.salvar(curso);

        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{cursoId}")
    public ResponseEntity<Void> remover(@PathVariable Long cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            return ResponseEntity.notFound().build();
        }

        registroCursoService.excluir(cursoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cursoId}/matrizes")
    public ResponseEntity<Curso> novaMatrizCurricular(@PathVariable Long cursoId, @Valid @RequestBody MatrizCurricular matriz) {

        return ResponseEntity.ok(registroCursoService.novaMatriz(cursoId, matriz));
    }
}
