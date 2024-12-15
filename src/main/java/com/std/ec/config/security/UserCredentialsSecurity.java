package com.std.ec.config.security;

import com.std.ec.model.entity.Usuario;
import com.std.ec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCredentialsSecurity implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca los datos del usuario en el repositorio utilizando el nombre de usuario proporcionado.
        // Si no encuentra al usuario, retorna null.
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        // Crea una lista vacía para almacenar las autoridades (roles) del usuario.
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // Verifica si el usuario encontrado no es nulo.
        if (usuario != null) {
            /* Si el usuario existe, transforma la lista de roles asociados al usuario
            en una lista de objetos `GrantedAuthority`, que Spring Security utiliza
            para manejar los permisos o roles.*/
            grantedAuthorities = usuario.getRoles().stream()
                    .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))// Convierte cada rol a `SimpleGrantedAuthority`.
                    .collect(Collectors.toList());// Colecta los resultados en una lista.
            /*
            Retorna un objeto `User` (implementación de `UserDetails`) con los siguientes datos:
             - username: nombre de usuario del objeto `Usuario`.
             - password: contraseña encriptada del objeto `Usuario`.
             - estado: si el usuario está activo o no.
             - true: indica que la cuenta no ha expirado.
             - true: indica que las credenciales no han expirado.
             - true: indica que la cuenta no está bloqueada.
             - grantedAuthorities: lista de roles o permisos del usuario.*/
            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.isEstado(),
                    true,
                    true,
                    true,
                    grantedAuthorities
            );
        }

        return null;
    }

}
