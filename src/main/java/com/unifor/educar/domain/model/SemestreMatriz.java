package com.unifor.educar.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class SemestreMatriz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "numero_semestre")
    private Long semestre;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "matriz_curricular_id")
    private MatrizCurricular matrizCurricular;

    @ManyToMany
    @JoinTable(name = "semestre_disciplina", joinColumns = @JoinColumn(name = "semestre_matriz_id"), inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
    private List<Disciplina> disciplinas;
}
