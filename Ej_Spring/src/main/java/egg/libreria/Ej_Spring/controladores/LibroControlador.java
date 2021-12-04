/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.Ej_Spring.controladores;

import egg.libreria.Ej_Spring.errores.ErrorServicio;
import egg.libreria.Ej_Spring.servicios.AutorServicio;
import egg.libreria.Ej_Spring.servicios.LibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Tomás
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroService ls;
    
    @Autowired
    private AutorServicio as;

    @GetMapping("/registro")
    public String reg_libro(ModelMap modelo) {
        try {
            List lista = as.buscarTodos();
            modelo.addAttribute("autores", lista);
            return "reg_libro.html";
            
        } catch (Exception e) {
        }
        return "reg_libro.html";
    }
    
    @PostMapping("/guardar")
    public String registro(ModelMap modelo, @RequestParam @Nullable Long ISBN, @RequestParam @Nullable String titulo, @RequestParam @Nullable Integer year, @RequestParam @Nullable Integer ejemplares, @RequestParam @Nullable String idAutor, @RequestParam(value = "") @Nullable String idEditorial) {
        try {

            ls.newLibro(ISBN, titulo, year, ejemplares, idAutor);
            return "exito.html";

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("ISBN", ISBN);
            modelo.put("titulo", titulo);
            modelo.put("year", year);
            modelo.put("ejemplares", ejemplares);
            modelo.put("idAutor", idAutor);
            modelo.put("idEditorial", idEditorial);
            return "reg_libro.html";

        }
    }

    @GetMapping("/alta/{id}")
    public String estado(@PathVariable String id, ModelMap modelo) throws ErrorServicio {
        try {
            ls.baja(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            modelo.put("error", e);
            return "redirect:/libro/lista";
        }

    }

    @GetMapping("/prestamo/{id}")
    public String pedir(ModelMap modelo, @PathVariable String id) {
        try {
            ls.prestar(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/libro/lista";
        }

    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws ErrorServicio {
        modelo.put("libro", ls.buscar_ID(id));
        return "libro_AC.html";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, String id, @RequestParam @Nullable Long ISBN, @RequestParam @Nullable String titulo, @RequestParam @Nullable Integer year, @RequestParam @Nullable Integer ejemplares, @RequestParam @Nullable String idAutor, String idEditorial) {
        try {
            ls.modify(id, ISBN, titulo, year, ejemplares, idAutor);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("ISBN", ISBN);
            modelo.put("titulo", titulo);
            modelo.put("year", year);
            modelo.put("ejemplares", ejemplares);
            modelo.put("idAutor", idAutor);
            modelo.put("idEditorial", idEditorial);
            return "libro_AC.html";

        }

    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        try {
            List lista = ls.buscarTodos();
            modelo.addAttribute("libros", lista);
            modelo.put("libros", lista);
            return "libros.html";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "index.html";
        }

    }

    

}
