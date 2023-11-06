package com.unifor.educar.core.security;

import com.unifor.educar.core.providers.JWTProvider;
import com.unifor.educar.domain.exception.NaoAutorizadoException;
import com.unifor.educar.domain.exception.NegocioException;
import com.unifor.educar.domain.model.Usuario;
import com.unifor.educar.domain.repository.UsuarioRepository;
import com.unifor.educar.domain.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null) {
            var subjectToken = this.jwtProvider.validateToken(header);

            if (subjectToken.isEmpty()) {
                throw new NaoAutorizadoException("Acesso não autorizado");
            }

            request.setAttribute("usuario_id", subjectToken);

            Usuario usuario = usuarioRepository.findById(Long.valueOf(subjectToken)).orElse(null);

            if (usuario != null) {

                Set<GrantedAuthority> authorities = usuario.getPapeis().stream()
                        .map(papel -> new SimpleGrantedAuthority(papel.getNome()))
                        .collect(Collectors.toSet());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        subjectToken, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }

        filterChain.doFilter(request, response);

    }
}
