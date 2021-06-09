/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construccionesdethompson;

/**
 *
 * @author emili
 */
public class Transicion {
    public int edo_inicial, edo_final;
    public char simbolo;
        
    public Transicion(int e1, int e2, char simbolo){
        this.edo_inicial = e1;
        this.edo_final  = e2;
        this.simbolo = simbolo;
    }
}
