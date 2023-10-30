package com.unifor.educar.domain.model;

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

    @ManyToOne
    private Curso curso;

    private String semestreReferencia;

    private Boolean ativo;

    @OneToMany(mappedBy = "matrizCurricular")
    private List<SemestreMatriz> semestres;

}
