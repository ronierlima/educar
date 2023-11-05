package com.unifor.educar.domain.service;

import com.unifor.educar.domain.exception.EntidadeNaoEncontradaException;
import com.unifor.educar.domain.model.Disciplina;
import com.unifor.educar.domain.repository.DisciplinaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public Disciplina buscar(Long disciplinaId) {
        return disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Disciplina não encontrada"));
    }

    @Transactional
    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    @Transactional
    public void excluir(Long disciplinaId) {
        disciplinaRepository.deleteById(disciplinaId);
    }
}
