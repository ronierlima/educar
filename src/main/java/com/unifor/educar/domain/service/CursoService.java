package com.unifor.educar.domain.service;

import com.unifor.educar.domain.exception.EntidadeNaoEncontradaException;
import com.unifor.educar.domain.exception.NegocioException;
import com.unifor.educar.domain.model.Curso;
import com.unifor.educar.domain.model.MatrizCurricular;
import com.unifor.educar.domain.repository.CursoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public Curso buscar(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Curso não encontrado"));
    }

    @Transactional
    public Curso salvar(Curso curso) {

        boolean nomeEmUso = cursoRepository.findByNome(curso.getNome())
                .filter(p -> !p.equals(curso))
                .isPresent();

        if (nomeEmUso) {
            throw new NegocioException("Já existe um curso com esse nome");
        }

        return cursoRepository.save(curso);
    }

    @Transactional
    public void excluir(Long cursoId) {
        cursoRepository.deleteById(cursoId);
    }

    @Transactional
    public Curso novaMatriz(Long cursoId, MatrizCurricular novaMatriz) {
        Curso curso = buscar(cursoId);

        novaMatriz.setCurso(curso);

        if (novaMatriz.getAtual()) {
            curso.getMatrizes().forEach(matriz -> matriz.setAtual(false));
        } else {
            List<MatrizCurricular> matrizesAtuais = curso.getMatrizes().stream()
                    .filter(MatrizCurricular::getAtual)
                    .toList();

            if (matrizesAtuais.isEmpty()) {
                throw new NegocioException("O curso precisa de uma matriz como atual");
            }
        }

        curso.getMatrizes().add(novaMatriz);

        return cursoRepository.save(curso);
    }

}
