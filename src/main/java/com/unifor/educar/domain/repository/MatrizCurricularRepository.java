package com.unifor.educar.domain.repository;

import com.unifor.educar.domain.model.MatrizCurricular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatrizCurricularRepository extends JpaRepository<MatrizCurricular, Long> {
}
