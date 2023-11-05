package com.unifor.educar.api.controller;

import com.unifor.educar.domain.model.Curso;
import com.unifor.educar.domain.model.MatrizCurricular;
import com.unifor.educar.domain.model.SemestreMatriz;
import com.unifor.educar.domain.service.MatrizService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/matrizes")
public class MatrizController {

    private final MatrizService matrizService;

    @PostMapping("/{matrizId}/semestres")
    public ResponseEntity<MatrizCurricular> novoSemestre(@PathVariable Long matrizId, @RequestBody SemestreMatriz semestreMatriz) {
        return ResponseEntity.ok(matrizService.novoSemestre(matrizId, semestreMatriz));
    }

    @PutMapping("/{matrizId}/semestres/{semestreId}")
    public ResponseEntity<Void> atualizarDisciplinasSemestre(@PathVariable Long matrizId, @PathVariable Long semestreId, @Valid @RequestBody SemestreMatriz semestreMatriz) {

        matrizService.atualizarDisciplinasSemestre(matrizId, semestreId, semestreMatriz);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{matrizId}/semestres/{semestreId}")
    public ResponseEntity<Void> removerSemestre(@PathVariable Long matrizId, @PathVariable Long semestreId) {

        matrizService.removerSemestre(matrizId, semestreId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{matrizId}/semestres/{semestreId}/disciplinas/{disciplinaId}")
    public ResponseEntity<Void> removerDisciplina(@PathVariable Long matrizId, @PathVariable Long semestreId, @PathVariable Long disciplinaId) {

        matrizService.removerDisciplina(matrizId, semestreId, disciplinaId);
        return ResponseEntity.noContent().build();
    }
}
