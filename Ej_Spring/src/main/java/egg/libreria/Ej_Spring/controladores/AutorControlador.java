/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.Ej_Spring.controladores;

import egg.libreria.Ej_Spring.errores.ErrorController;
import egg.libreria.Ej_Spring.errores.ErrorServicio;
import egg.libreria.Ej_Spring.repositorios.AutorRepositorio;
import egg.libreria.Ej_Spring.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Tomás
 */
@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio as;

    @GetMapping("/registro")
    public String registro() {
        return "reg_autores.html";
    }

    @PostMapping("/cargar")
    public String registro(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio, ErrorController {
        try {
            as.newAutor(nombre);
            return "exito.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            return "reg_autores.html";

        }

    }

}
