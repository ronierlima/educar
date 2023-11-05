package com.unifor.educar.api.controller;

import com.unifor.educar.domain.model.Curso;
import com.unifor.educar.domain.model.MatrizCurricular;
import com.unifor.educar.domain.model.Usuario;
import com.unifor.educar.domain.repository.CursoRepository;
import com.unifor.educar.domain.repository.UsuarioRepository;
import com.unifor.educar.domain.service.CursoService;
import com.unifor.educar.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioServices;
    private final UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(usuarioServices.login(usuario));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionar(@Valid @RequestBody Usuario usuario) {
        return usuarioServices.salvar(usuario);
    }

}
