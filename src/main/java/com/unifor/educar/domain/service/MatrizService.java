package com.unifor.educar.domain.service;

import com.unifor.educar.domain.exception.EntidadeNaoEncontradaException;
import com.unifor.educar.domain.exception.NegocioException;
import com.unifor.educar.domain.model.Disciplina;
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

        verificarDuplicacaoSemestre(matriz, novaSemestre);

        novaSemestre.setMatrizCurricular(matriz);

        matriz.getSemestres().add(novaSemestre);

        return salvarMatriz(matriz);
    }

    @Transactional
    public void atualizarDisciplinasSemestre(Long matrizId, Long semestreId, SemestreMatriz semestreMatriz) {
        MatrizCurricular matriz = buscar(matrizId);

        SemestreMatriz semestre = obterSemestre(matriz, semestreId);

        verificarDuplicacaoDisciplinas(matriz, semestre, semestreMatriz);

        semestre.setDisciplinas(semestreMatriz.getDisciplinas());

        salvarMatriz(matriz);
    }

    @Transactional
    public void removerSemestre(Long matrizId, Long semestreId) {
        MatrizCurricular matriz = buscar(matrizId);

        SemestreMatriz semestre = obterSemestre(matriz, semestreId);

        matriz.getSemestres().remove(semestre);

        salvarMatriz(matriz);
    }

    @Transactional
    public void removerDisciplina(Long matrizId, Long semestreId, Long disciplinaId) {
        MatrizCurricular matriz = buscar(matrizId);

        SemestreMatriz semestre = obterSemestre(matriz, semestreId);

        Disciplina disciplina = obterDisciplina(semestre, disciplinaId);

        semestre.getDisciplinas().remove(disciplina);

        salvarMatriz(matriz);
    }

    private void verificarDuplicacaoSemestre(MatrizCurricular matriz, SemestreMatriz novoSemestre) {
        if (matriz.getSemestres().stream().anyMatch(semestre -> semestre.getSemestre().equals(novoSemestre.getSemestre()))) {
            throw new NegocioException("Número de semestre duplicado: " + novoSemestre.getSemestre());
        }
    }

    private SemestreMatriz obterSemestre(MatrizCurricular matriz, Long semestreId) {
        return matriz.getSemestres().stream()
                .filter(s -> s.getId().equals(semestreId))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Semestre não encontrado"));
    }

    private Disciplina obterDisciplina(SemestreMatriz semestre, Long disciplinaId) {
        return semestre.getDisciplinas().stream()
                .filter(d -> d.getId().equals(disciplinaId))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Disciplina não encontrada"));
    }

    private void verificarDuplicacaoDisciplinas(MatrizCurricular matriz, SemestreMatriz semestre, SemestreMatriz semestreAtualizado) {
        for (Disciplina novaDisciplina : semestreAtualizado.getDisciplinas()) {
            for (SemestreMatriz outroSemestre : matriz.getSemestres()) {
                if (outroSemestre != semestre && disciplinaJaExisteNoSemestre(outroSemestre, novaDisciplina)) {
                    throw new NegocioException("A disciplina '" + novaDisciplina.getNome() + "' já está no semestre " + outroSemestre.getSemestre());
                }
            }
        }
    }

    private boolean disciplinaJaExisteNoSemestre(SemestreMatriz semestre, Disciplina disciplina) {
        return semestre.getDisciplinas().stream()
                .anyMatch(d -> d.getId().equals(disciplina.getId()));
    }

    private MatrizCurricular salvarMatriz(MatrizCurricular matriz) {
        return matrizRepository.save(matriz);
    }
}
