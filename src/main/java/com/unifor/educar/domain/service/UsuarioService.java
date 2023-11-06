package com.unifor.educar.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.unifor.educar.domain.exception.EntidadeNaoEncontradaException;
import com.unifor.educar.domain.exception.NegocioException;
import com.unifor.educar.domain.model.Usuario;
import com.unifor.educar.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    @Value("${security.token.secret-key}")
    private String secretKey;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario buscar(Long cursoId) {
        return usuarioRepository.findById(cursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario não encontrado"));
    }

    public Usuario buscarPeloEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Email/Senha Inválidos"));
    }

    public String login(Usuario usuario) throws AuthenticationException {

        Usuario usuarioEncontrado = buscarPeloEmail(usuario.getEmail());

        if (!passwordEncoder.matches(usuario.getSenha(), usuarioEncontrado.getSenha())) {
            throw new AuthenticationException("email/senha incorretos");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withIssuer("educar")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(usuarioEncontrado.getId().toString())
                .sign(algorithm);

    }


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
