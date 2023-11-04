package com.unifor.educar.domain.service;

import com.unifor.educar.domain.exception.EntidadeNaoEncontradaException;
import com.unifor.educar.domain.exception.NegocioException;
import com.unifor.educar.domain.model.MatrizCurricular;
import com.unifor.educar.domain.model.SemestreMatriz;
import com.unifor.educar.domain.repository.MatrizCurricularRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MatrizService {

    private final MatrizCurricularRepository matrizRepository;

    public MatrizCurricular buscar(Long matrizId) {
        return matrizRepository.findById(matrizId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Matriz não encontrada"));
    }


    @Transactional
    public MatrizCurricular novoSemestre(Long matrizId, SemestreMatriz novaSemestre) {
        MatrizCurricular matriz = buscar(matrizId);

        if (matriz.getSemestres().stream().anyMatch(semestre -> semestre.getSemestre().equals(novaSemestre.getSemestre()))) {
            throw new NegocioException("Número de semestre duplicado: " + novaSemestre.getSemestre());
        }

        novaSemestre.setMatrizCurricular(matriz);

        matriz.getSemestres().add(novaSemestre);

        return matrizRepository.save(matriz);
    }

}
