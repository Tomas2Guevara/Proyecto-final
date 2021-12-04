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
public class ErrorController extends Exception{
    public ErrorController (String m){
        super(m);
    }
}
