package com.unifor.educar.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.unifor.educar.domain.exception.EntidadeNaoEncontradaException;
import com.unifor.educar.domain.exception.NaoAutorizadoException;
import com.unifor.educar.domain.exception.NegocioException;
import com.unifor.educar.domain.model.Papel;
import com.unifor.educar.domain.model.Usuario;
import com.unifor.educar.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    @Value("${security.token.secret-key}")
    private String secretKey;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario buscar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario não encontrado"));
    }


    public String login(Usuario usuario)  {

        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow(() -> new NaoAutorizadoException("Email/Senha Inválidos"));;

        if (!passwordEncoder.matches(usuario.getSenha(), usuarioEncontrado.getSenha())) {
            throw new NaoAutorizadoException("email/senha incorretos");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        List<String> papeis = (usuarioEncontrado.getPapeis() != null)
                ? usuarioEncontrado.getPapeis().stream().map(Papel::getNome).collect(Collectors.toList())
                : Collections.emptyList();

        return JWT.create()
                .withIssuer("educar")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(usuarioEncontrado.getId().toString())
                .withClaim("usuario_id", usuarioEncontrado.getId())
                .withClaim("usuario_nome", usuarioEncontrado.getNome())
                .withClaim("roles", papeis)
                .withClaim("curso_id", usuarioEncontrado.getCurso().getId())
                .sign(algorithm);

    }


    @Transactional
    public Usuario salvar(Usuario usuario) {

        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(c -> {
                    throw new NegocioException("Já existe um usuário cadastrado com esse email");
                });

        var password = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(password);

        return usuarioRepository.save(usuario);

    }


}
