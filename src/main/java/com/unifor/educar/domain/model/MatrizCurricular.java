package com.unifor.educar.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class MatrizCurricular {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private String descricao;
    private Boolean atual;

    @Column(name = "numero_semestres")
    private Long numeroSemestres;

    @OneToMany(mappedBy = "matrizCurricular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SemestreMatriz> semestres;
    
    public long getTotalDisciplinas() {
        if (semestres == null) return 0;

        return semestres.stream()
                .filter(semestre -> semestre.getDisciplinas() != null)
                .flatMap(semestre -> semestre.getDisciplinas().stream())
                .count();
    }

    public long getTotalCargaHoraria() {
        if (semestres == null) return 0;

        return semestres.stream()
                .filter(semestre -> semestre.getDisciplinas() != null)
                .flatMap(semestre -> semestre.getDisciplinas().stream())
                .filter(disciplina -> disciplina.getCargaHoraria() != null)
                .mapToLong(Disciplina::getCargaHoraria)
                .sum();
    }

}
