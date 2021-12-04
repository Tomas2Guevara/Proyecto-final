/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.libreria.Ej_Spring.errores;

/**
 *
 * @author Tomás
 */
public class ErrorServicio extends Exception{
    public ErrorServicio (String m){
        super(m);
    }
}
