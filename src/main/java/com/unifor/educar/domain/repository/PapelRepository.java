package com.unifor.educar.domain.repository;

import com.unifor.educar.domain.model.Papel;
import com.unifor.educar.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {

}
