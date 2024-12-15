package com.std.ec.service.impl;

import com.std.ec.model.entity.Rol;
import com.std.ec.model.entity.Ruta;
import com.std.ec.repository.RutaRepository;
import com.std.ec.service.IRutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RutaService  implements IRutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Override
    public List<Ruta> listar() {
        return (List<Ruta>) rutaRepository.findAll();
    }

    // Método que genera un mapeo de rutas y los roles asociados a ellas
    @Override
    public Map<String, List<String>> getRutaRolMappings(){
        // Se crea un mapa vacío donde se almacenarán las rutas como claves
        // y una lista de nombres de roles como valores.
        Map<String, List<String>> map = new HashMap<>();
        // Se obtiene una lista de objetos Ruta a través del método listar().
        List<Ruta> rutas = listar();
        // Se recorre cada objeto Ruta en la lista.
        for (Ruta ruta : rutas) {
            // Verifica si la lista de roles de la ruta no está vacía.
            if (!ruta.getRoles().isEmpty()){
                List<String> roles = ruta.getRoles().stream()
                        .map(Rol::getNombre)// Obtiene el nombre de cada rol.
                        .collect(Collectors.toList());// Recoge los nombres en una lista.
                // Agrega al mapa la URL de la ruta como clave
                // y la lista de nombres de roles como valor.
                map.put(ruta.getRutaUrl(), roles);
            }
        }
        // Devuelve el mapa con las asociaciones de rutas y roles.
        return map;
    }
}
