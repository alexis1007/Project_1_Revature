package org.example.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.example.Service.JwtService;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private static final List<String> PUBLIC_URLS = Arrays.asList("/api/auth");
    
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        
        if (PUBLIC_URLS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener header Authorization
        String authHeader = request.getHeader("Authorization");

        // Si no hay token, continuar con la cadena de filtros
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = null;
        
        try {
            username = jwtService.extractUsername(jwt);
            Optional<User> user = userRepository.findByUsername(username);
            
            if (username != null && user.isPresent() && jwtService.validateToken(jwt, username)) {
                String userRole = user.get().getUserType().getUserType();
                // Asegurar que tenga el prefijo ROLE_
                String roleName = userRole.startsWith("ROLE_") ? userRole : "ROLE_" + userRole;
                
                log.debug("User role: {}, formatted to: {}", userRole, roleName);
                
                // Crear autoridades para Spring Security
                List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(roleName)
                );
                
                // Crear token de autenticación y establecerlo en el contexto
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // También guardar el usuario para referencia
                request.setAttribute("user", user.get());
                
                // ***IMPORTANTE: SIEMPRE continuar la cadena de filtros***
            }
        } catch (Exception e) {
            log.error("Error procesando token JWT", e);
        }
        
        // Siempre continuar con la cadena de filtros, se haya autenticado o no
        filterChain.doFilter(request, response);
    }
}
