package com.unifor.educar.api.controller;

import com.unifor.educar.domain.model.MatrizCurricular;
import com.unifor.educar.domain.model.SemestreMatriz;
import com.unifor.educar.domain.service.MatrizService;
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
}
