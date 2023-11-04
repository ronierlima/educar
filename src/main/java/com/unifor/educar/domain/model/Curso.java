package com.unifor.educar.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Curso {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private Long semestres;

    private String descricao;

    @NotNull
    private Long cargaHoraria;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Titulacao titulacao;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MatrizCurricular> matrizes;

    public MatrizCurricular getMatrizAtual() {
        if (matrizes == null) {
            return null;
        }

        return matrizes.stream()
                .filter(matriz -> Boolean.TRUE.equals(matriz.getAtual()))
                .findFirst()
                .orElse(null);
    }

}
