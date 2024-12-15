package com.std.ec.handler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Custom404 implements ErrorController {

    //Este metodo detecta la peticion por defecto en la ruta /error y la reemplazamos
    @RequestMapping("/error")
    public String getErrorPath() {
        // Esto mantendrá la URL actual y renderizará la vista 404.xhtml
        return "/404.xhtml";
    }

}
