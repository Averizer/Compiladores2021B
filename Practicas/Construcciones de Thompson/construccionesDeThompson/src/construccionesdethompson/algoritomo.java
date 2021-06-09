/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construccionesdethompson;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;


public class algoritomo {
    
    
    public static boolean caracter(char c){ return c >= 'a' && c <= 'z';}
    public static boolean alfabeto(char c){ return caracter(c) || c == 'E';}
    public static boolean operador(char c){ return c == '(' || c == ')' || c == '*' || c == '|';}
    public static boolean caracterValido(char c){ return alfabeto(c) || operador(c);}
    public static boolean expresionValida(String regex){
        if (regex.isEmpty()){
            System.out.println("Expresión vacía");
            return false; 
        }
        for (char c: regex.toCharArray()){
            if (!caracterValido(c)){
                System.out.println("Caracter desconocido");
                return false;
            }
        }
            
        return true;
    }
    
    /*
        Construción de la cerradurrra de Kleene
        Esta construcción crea un mini automata y toma en cuenta las reglas para
        formar una construcción de cerradura.
        Transición del estado 0 al estado final
        Transición reversa del estado final n al estado inicial n
        Transición nueva inicial con E
        Transición nueva final con E
        Se agregan las transiciciones que se tenían antes al intermedio
    
    */
    public static automata construccionKleene(automata a){
        automata construccion = new automata(a.estados.size()+2);
        construccion.transciones.add(new Transicion(0, 1,'E'));
        
        for(Transicion t: a.transciones){
            construccion.transciones.add(new Transicion(t.edo_inicial + 1, 
                    t.edo_final + 1, t.simbolo));
        }
        construccion.transciones.add(new Transicion(a.estados.size(), a.estados.size()+1, 'E'));
        construccion.transciones.add(new Transicion(a.estados.size(), 1, 'E'));
        construccion.transciones.add(new Transicion(0, a.estados.size()+1, 'E'));
        
        construccion.estadoFinal = a.estados.size() + 1;
        return construccion;
    }
    
    /*
        Construccion de concatenacion
        La concatenación trabaja de izquierda a derecha 
        por lo que se borra el primer estado del segundo automata para
        concatenar el ultimo del primero y volverlo el nuevo primero del 
        segundo.
        Seguido a eso se agegan las trasiciones del segundo automata al primero
        y finalmente se arreglan los estados del segundo al primero, sumando
        los estados + 1 por el eliminado.
        Se corrige el estado final del automata (principal) .
    */
    public static automata construccionConcatenar(automata a, automata b){
        b.estados.remove(0);
        for(Transicion t : b.transciones){
            a.transciones.add(new Transicion(t.edo_inicial + a.estados.size()-1,
                    t.edo_final + a.estados.size()-1, t.simbolo));
        }
        
        for(Integer e: b.estados){
            a.estados.add(e + a.estados.size() + 1);
        }
        
        a.estadoFinal = a.estados.size()+ b.estados.size() -  2;
        return a;
    }
    
    /*
        Construcción de OR
        Esta construcción toma en cuenta que primero se agrega un estado 0 
        con transición E.
        Se agrega el primer automata en la parte de "Arriba" y una transición final
        Se parte la rama, se agrega el segundo automata y se une con el estado final
    */ 
    public static automata construccionOr(automata a, automata b){
        automata construccion = new automata(a.estados.size()+b.estados.size()+2);
        construccion.transciones.add(new Transicion(0, 1, 'E'));
        
        for(Transicion t : a.transciones){
            construccion.transciones.add(new Transicion(t.edo_inicial + 1, t.edo_final +1, t.simbolo));
        }
        
        construccion.transciones.add(new Transicion(a.estados.size(), a.estados.size()+b.estados.size() + 1, 'E'));
        construccion.transciones.add(new Transicion(0, a.estados.size()+1, 'E'));
        
        for(Transicion t: b.transciones){
            construccion.transciones.add(new Transicion(t.edo_inicial + a.estados.size() + 1, t.edo_final + a.estados.size()+1, t.simbolo));      
        }
        
        construccion.transciones.add(new Transicion(b.estados.size()+a.estados.size(), a.estados.size()+b.estados.size()+1,'E'));
        construccion.estadoFinal = a.estados.size() + b.estados.size() + 1 ;
        return construccion;
    }
    
    /*
        Construcción total del automata
        Opera la expresión en su totalidad, ocupa pilas para saber la precedencia
        y poder operar primero izqauierda y luego derecha (Como es "correcto").
    */
    public static automata contruccionTotal(String expresion){
        if(!expresionValida(expresion)){
            System.out.println("La expresión es invalida");
            return new automata();
        }
        
        Stack <Character> operadores = new Stack <Character>();
        Stack <automata> operando = new Stack <automata>();
        Stack <automata> pila_concatenacion = new Stack <automata>();
        
        boolean banderaConcatenacion = false;
        char operacion, evaluando;
        int cuenta_balance = 0;
        automata aut1, aut2;
        
        for(int i = 0; i < expresion.length(); i++){
            evaluando = expresion.charAt(i);
            if(alfabeto(evaluando)){
                operando.push(new automata(evaluando));
                if(banderaConcatenacion){
                    operadores.push('.');
                }else{
                    banderaConcatenacion= true;
                }
            }else{
                if(evaluando == ')'){
                    banderaConcatenacion = false;
                    if(cuenta_balance == 0 ){
                        System.out.println("HAY UN PROBELMA EN EL BALANCE DE PARENTESIS");
                        System.exit(1);
                    }else{
                        cuenta_balance--;
                    }
                    
                    while(!operadores.empty() && operadores.peek() != '('){
                        operacion = operadores.pop();
                        if(operacion == '.'){
                            aut2 = operando.pop();
                            aut1 = operando.pop();
                            operando.push(construccionConcatenar(aut1, aut2));
                        }else if(operacion== '|'){
                            aut2 = operando.pop();
                            
                            if(!operadores.empty() && operadores.peek()=='.'){
                                pila_concatenacion.push(operando.pop());
                                
                                while(!operadores.empty()&&operadores.peek()=='.'){
                                    pila_concatenacion.push(operando.pop());
                                    operadores.pop();
                                }
                                aut1=construccionConcatenar(pila_concatenacion.pop()
                                        , pila_concatenacion.pop());
                                
                                while(pila_concatenacion.size() > 0){
                                    aut1 = construccionConcatenar(aut1, pila_concatenacion.pop());
                                }
                            }else{
                                aut1 = operando.pop();
                            }
                            operando.push(construccionOr(aut1, aut2));
                        }
                    }
                }
                else if(evaluando=='*'){
                    operando.push(construccionKleene(operando.pop()));
                    banderaConcatenacion=true;
                }else if(evaluando == '('){
                    operadores.push(evaluando);
                    cuenta_balance++;
                }else if(evaluando == '|'){
                    operadores.push(evaluando);
                    banderaConcatenacion=false;
                }
            }
        } 
        
        while(operadores.size()>0){
            if(operando.empty()){
                System.out.println("No hay balance en los operaciones con los operadores");
                System.exit(1);
            }
            operacion = operadores.pop();
            if(operacion == '.'){
                aut2 = operando.pop();
                aut1 = operando.pop();
                operando.push(construccionConcatenar(aut1, aut2));
            }else if(operacion == '|'){
                aut2 = operando.pop();
                if(!operadores.empty() && operadores.peek()=='.'){
                    pila_concatenacion.push(operando.pop());
                    while(!operadores.empty()&&operadores.peek()=='.'){
                        pila_concatenacion.push(operando.pop());
                        operadores.pop();
                    }
                    aut1=construccionConcatenar(pila_concatenacion.pop()
                            , pila_concatenacion.pop());
                    while(pila_concatenacion.size()>0){
                        aut1 = construccionConcatenar(aut1, pila_concatenacion.pop());
                    }
                }else{
                    aut1 = operando.pop();
                }
                operando.push(construccionOr(aut1, aut2));
            }
        }
        return operando.pop();
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String entrada;
        System.out.println("\nIngresa una ER para evaluar " +
            "CARACTERES = Aa -> Zz & E para vacío "+"\n*  OPERACIONES =" + 
            "\n ( ) * | " + "\n \n\"SALIR\" para terminar el programa");
        
        while(s.hasNextLine()){
            
            entrada = s.nextLine();
            if (entrada.equals(":q") || entrada.equals("SALIR"))
                break;
            
            automata a = contruccionTotal(entrada);
            System.out.println("\nNFA:");
            
            try {
                a.getAutomata(entrada);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(algoritomo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            System.out.println("\nIngresa una ER para evaluar " +
            "CARACTERES = Aa -> Zz & E para vacío "+"\n*  OPERACIONES =" + 
            "\n ( ) * | " + "\n \n\"SALIR\" para terminar el programa");
        
        }
    }
         
    
}
