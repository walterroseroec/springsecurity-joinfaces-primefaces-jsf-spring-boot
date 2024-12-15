package com.std.ec.controller;

import jakarta.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("loginMB")
@ViewScoped
public class LoginBean implements Serializable {

    public String getUserName(){
        // Obtiene los datos del usuario autenticado actualmente en el contexto de seguridad
        Object datoUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Verifica si el objeto 'datoUser' es una instancia de UserDetails
        if (datoUser instanceof UserDetails){
            // Si es una instancia de UserDetails, se retorna el nombre de usuario asociado a la sesión
            return ((UserDetails)datoUser).getUsername();
        }
        // Si 'datoUser' no es una instancia de UserDetails, se convierte a String y se retorna
        return datoUser.toString();
    }

}
