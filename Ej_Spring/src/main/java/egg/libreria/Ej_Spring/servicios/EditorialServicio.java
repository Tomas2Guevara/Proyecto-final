/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.Ej_Spring.servicios;

import egg.libreria.Ej_Spring.entidades.Editorial;
import egg.libreria.Ej_Spring.errores.ErrorServicio;
import egg.libreria.Ej_Spring.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomás
 */
@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio er;
    
    @Transient
    public void nueva(String nombre) throws ErrorServicio{
        validar(nombre);
        Editorial e = new Editorial();
        e.setNombre(nombre);
        e.setAlta(true);
        er.save(e);
    }
    
    private void validar(String n) throws ErrorServicio{
        if(n == null || n.isEmpty()){
            throw new ErrorServicio("Nombre vacío");
        }
    }
    
    
}
