package com.std.ec.config.filter;

import com.std.ec.service.IRutaService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AutorizacionFilter extends OncePerRequestFilter {

    @Autowired
    private IRutaService rutaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Obtener la URL solicitada
        String requestURI = request.getRequestURI();

        // Cargar rutas y roles desde la base de datos
        Map<String, List<String>> mapaRutas = rutaService.getRutaRolMappings();

        // Encontrar roles autorizados para la URL solicitad
        List<String> authRoles = mapaRutas.get(requestURI);
        if (authRoles != null) {
            // Verificar si el usuario autenticado tiene uno de los roles permitidos
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                boolean accesoRol = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> authRoles.contains(grantedAuthority.getAuthority()));

                if (!accesoRol) {
                    // Si no tiene permiso, redirigir a la página de acceso denegado
                    request.getRequestDispatcher("/403.xhtml").forward(request, response);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }



}
