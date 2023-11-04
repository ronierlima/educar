package com.unifor.educar.domain.service;

import com.unifor.educar.domain.repository.MatrizCurricularRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistroMatrizCurricularService {

    private final MatrizCurricularRepository matrizCurricularRepository;
}
