/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.Ej_Spring.servicios;

import egg.libreria.Ej_Spring.entidades.Autor;
import egg.libreria.Ej_Spring.errores.ErrorServicio;
import egg.libreria.Ej_Spring.repositorios.AutorRepositorio;
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
public class AutorServicio {

    @Autowired
    private AutorRepositorio ar;

    @Transactional
    public void newAutor(String name) throws ErrorServicio {
        validar(name);

        Autor a = new Autor();
        a.setNombre(name);
        a.setAlta(true);

        ar.save(a);
    }

    @Transactional
    public void modify(String id, String name) throws ErrorServicio {
        Optional<Autor> ans = ar.findById(id);
        if (ans.isPresent()) {
            validar(name);
            Autor a = ar.findById(id).get();
            a.setNombre(name);

            ar.save(a);

        } else {
            throw new ErrorServicio("Libro no encontrado");
        }
    }
    
    @Transactional
    public List<Autor> buscarTodos(){
        return ar.findAll();
    }

    @Transactional
    public void baja(String id) throws ErrorServicio {
        Optional<Autor> ans = ar.findById(id);
        if (ans.isPresent()) {
            Autor a = ar.findById(id).get();
            if (a.isAlta()) {
                a.setAlta(false);
                ar.save(a);
            }else{
                a.setAlta(false);
                ar.save(a);
            }
        } else {
            throw new ErrorServicio("No se encontró el autor");
        }
    }

    @Transactional
    public Autor buscar_ID(String id) {
        return ar.getById(id);
    }

    @Transactional
    public Autor buscar(String name) throws ErrorServicio {
        validar(name);
        Autor ans = ar.findByName(name);
        if (ans == null) {
            throw new ErrorServicio("No se encontro el autor");
        } else {
            return ans;
        }
    }

    private void validar(String n) throws ErrorServicio {
        if (n == null || n.isEmpty()) {
            throw new ErrorServicio("Nombre vacío");
        }
    }
}
