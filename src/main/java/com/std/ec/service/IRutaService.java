package com.std.ec.service;

import com.std.ec.model.entity.Ruta;

import java.util.List;
import java.util.Map;

public interface IRutaService {

    List<Ruta> listar();

    Map<String, List<String>> getRutaRolMappings();

}
