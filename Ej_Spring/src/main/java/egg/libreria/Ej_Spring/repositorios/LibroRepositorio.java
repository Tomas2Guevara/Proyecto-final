/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.Ej_Spring.repositorios;

import egg.libreria.Ej_Spring.entidades.Autor;
import egg.libreria.Ej_Spring.entidades.Editorial;
import egg.libreria.Ej_Spring.entidades.Libro;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tomás
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro find_titulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor = :autor")
    public List<Libro> find_autor(@Param("autor") String autor);    
    
}
