/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.Ej_Spring.servicios;

import egg.libreria.Ej_Spring.entidades.Autor;
import egg.libreria.Ej_Spring.entidades.Editorial;
import egg.libreria.Ej_Spring.entidades.Libro;
import egg.libreria.Ej_Spring.errores.ErrorServicio;
import egg.libreria.Ej_Spring.repositorios.AutorRepositorio;
import egg.libreria.Ej_Spring.repositorios.EditorialRepositorio;
import egg.libreria.Ej_Spring.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tomás
 */
@Service
public class LibroService {

    @Autowired
    private LibroRepositorio lr;
    @Autowired
    private AutorRepositorio ar;
    @Autowired
    private EditorialRepositorio er;
    @Autowired
    private AutorServicio as;

    @Transactional
    public void newLibro(Long isbn, String titulo, Integer year, Integer ejemplares, String ID_autor) throws ErrorServicio {
        Optional<Autor> ansA = ar.findById(ID_autor);
//        Optional<Editorial> ansE = er.findById(ID_edit);
        if (ansA.isPresent()) { //&& ansE.isPresent()) {           

            validar(isbn, titulo, year, ejemplares);

            Libro l = new Libro();
            l.setISBN(isbn);
            l.setTitulo(titulo);
            l.setYear(year);
            l.setEjemplares(ejemplares);
            l.setEjemplaresPrestados(0);
            l.setEjemplaresRestantes(ejemplares);
            l.setAlta(true);
            l.setAutor(ar.findById(ID_autor).get());
//            l.setEditorial(er.findById(ID_edit).get());            

            lr.save(l);
        } else {
            throw new ErrorServicio("Debe ingresar un autor y una Editorial existente");
        }

    }

    @Transactional
    public void prestar(String id) throws ErrorServicio {
        Optional<Libro> ans = lr.findById(id);
        if (ans.isPresent()) {            
            Libro l = lr.findById(id).get();
            if ((l.getEjemplaresRestantes()- 1) <= 0) {
                l.setAlta(false);
                throw new ErrorServicio("No quedan libros");
            } else {                
                l.setEjemplaresRestantes(l.getEjemplaresRestantes()- 1);
                l.setEjemplaresPrestados(l.getEjemplaresPrestados()+1);
                
                lr.save(l);
            }
        } else {
            throw new ErrorServicio("No se encontró el libro");
        }
    }

    @Transactional
    public Libro buscar_ID(String n) throws ErrorServicio {
        Optional<Libro> ans = lr.findById(n);

        if (ans.isPresent()) {
            Libro a = lr.findById(n).get();
            return a;
        } else {
            throw new ErrorServicio("No se encontró el libro");
        }
    }

    @Transactional
    public List<Libro> buscarTodos() {
        return lr.findAll();
    }

    @Transactional
    public void baja(String id) throws ErrorServicio {
        Optional<Libro> ans = lr.findById(id);
        if (ans.isPresent()) {
            Libro a = lr.findById(id).get();
            if (a.isAlta()) {
                a.setAlta(false);
                lr.save(a);
            } else {
                a.setAlta(true);
                lr.save(a);
            }
        } else {
            throw new ErrorServicio("No se encontró el libro");
        }

    }

    @Transactional
    public void modify(String id, Long isbn, String titulo, Integer year, Integer ejemplares, String id_autor) throws ErrorServicio {
        Optional<Libro> ans = lr.findById(id);
        if (ans.isPresent()) {
            validar(isbn, titulo, year, ejemplares);
            Libro l = lr.findById(id).get();
            l.setISBN(isbn);
            l.setTitulo(titulo);
            l.setYear(year);
            l.setEjemplares(ejemplares);
            l.setEjemplaresRestantes(ejemplares - l.getEjemplaresPrestados());
            l.setAutor(as.buscar_ID(id_autor));
            l.setAlta(true);

            lr.save(l);

        } else {
            throw new ErrorServicio("Libro no encontrado");
        }
    }

    @Transactional
    public Libro buscar_name(String n) throws ErrorServicio {
        if (n == null || n.isEmpty()) {
            throw new ErrorServicio("Debe ingresar un nombre");
        } else {
            return lr.find_titulo(n);
        }
    }

    @Transactional
    public List<Libro> buscar_autor(String n) throws ErrorServicio {
        if (n == null || n.isEmpty()) {
            throw new ErrorServicio("Debe ingresar un nombre");
        } else {
            return lr.find_autor(n);
        }
    }

    @Transactional
    private void validar(Long isbn, String titulo, Integer year, Integer ejemplares) throws ErrorServicio {
        if (isbn == null || isbn <= 0) {
            throw new ErrorServicio("ISBN inválido");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("Titulo inválido");
        }

        if (year == null || year <= 0) {
            throw new ErrorServicio("Año inválido");
        }

        if (ejemplares == null || ejemplares <= 0) {
            throw new ErrorServicio("Ejemplares inválido");
        }
    }

}
