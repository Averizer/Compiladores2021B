/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmodelossubconjuntos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 *
 * @author emili
 */
public class Algoritmo {
    public static ArrayList<Integer> afndFinales    = new ArrayList<>();
    public static ArrayList<Integer> efinalafnd    = new ArrayList<>();
    public static ArrayList<String> alfabeto       = new ArrayList<>();
    public static ArrayList<Integer> afdFinales    = new ArrayList<>();
    public static ArrayList<Integer> afdNoFinales   = new ArrayList<>();
    public static automata afnd = new automata();
    public static automata afd = new automata();
    /*
        Leer Archivo
        Esta fucnion ayuadra para poder leer el arvivo de salida del programa
        anterir, y guardara en listas los estados y los simbolos para poder 
        iterar y que sea más sencilla le ejecución
    */
    public static void LeerArchivo() throws FileNotFoundException, IOException{
        String archivo = "C:\\Users\\emili\\OneDrive - Instituto Politecnico Nacional\\"
                + "Desktop\\Compiladores2021B\\Compiladores2021B\\Practicas\\AFND.csv";
        int nfila = 0;
        BufferedReader bufferLectura = new BufferedReader(
                new FileReader(archivo));
        String linea = bufferLectura.readLine();
  
        while (linea != null) {
         // Sepapar la linea leída con el separador definido previamente
         String[] campos = linea.split(","); 
         if(nfila == 0){
             for(String s : campos){ 
                 afnd.alfabeto.add((""+s).trim());
             }
             nfila ++;
         } else if(nfila == 1){
              for(String s : campos){ 
                 efinalafnd.add(Integer.parseInt(s));
             }
             nfila ++;
         }else {
             Transicion t = new Transicion(Integer.parseInt(campos[0]), 
                     Integer.parseInt(campos[2]), 
                     campos[1]);
             afnd.transciones.add(t);
             afndFinales.add(Integer.parseInt(campos[2]));
         }

         // Volver a leer otra línea del fichero
         linea = bufferLectura.readLine();
        }
        afnd.estadoFinal = Collections.max(afndFinales);  
    }
    
    public static ArrayList<Integer> Cerradura(ArrayList<Integer> evaluando){
        Stack <Integer> estados_por_evaluar = new Stack <Integer>();
        ArrayList<Integer> resultado = new ArrayList<>();
        boolean bandera=true;
        
        estados_por_evaluar.addAll(evaluando);
        resultado.addAll(evaluando);
        
        while(bandera){
            int evaluando_en_cerradura = estados_por_evaluar.pop();
            for(Transicion t: afnd.transciones){
                if(t.edo_inicial == evaluando_en_cerradura &&
                    t.simbolo.contains("E")){
                    if(!resultado.contains(t.edo_final))
                        resultado.add(t.edo_final);
                    estados_por_evaluar.add(t.edo_final);
                } 
            }
            if(estados_por_evaluar.empty())
                bandera = false;
            
        }
        return resultado;
    }
    
    public static void EjecutarAlgoritmo(){
        Queue <ArrayList<Integer>> estados_por_evaluar = new LinkedList<ArrayList<Integer>>() ;
        ArrayList<Integer> estado_antes_cerradura = new ArrayList<>();
        ArrayList<ArrayList<Integer>> estados_finales = 
                new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> estado_temporal = new ArrayList<>();
        boolean bandera = true;
        
        estado_antes_cerradura.add(0);
        estado_temporal = Cerradura(estado_antes_cerradura);
        estados_por_evaluar.add(estado_temporal);
        estados_finales.add(estado_temporal);
        
        for(int e: efinalafnd){
            if (estado_temporal.contains(e)) {
                afdFinales.add(0);
            }
        }
        
        while(bandera){
            ArrayList<Integer> estado_evaluado = estados_por_evaluar.remove();
            for(String a: afnd.alfabeto){ //Para cada letra del alfabeto 
               estado_antes_cerradura.clear();
               for(Integer e: estado_evaluado){// Evaluar cada estado
                   for(Transicion t: afnd.transciones){ //Obtener los estados antes de la cerradura
                       if(e == t.edo_inicial && a.contains(t.simbolo)){
                           if(!estado_antes_cerradura.contains(t.edo_final))//Si no
                            estado_antes_cerradura.add(t.edo_final);
                       }
                   }
               }
               if(!estado_antes_cerradura.isEmpty()){
                   estado_temporal = Cerradura(estado_antes_cerradura);
                   if(estados_finales.contains(estado_temporal)){
                       afd.transciones.add(
                               new Transicion(
                                       estados_finales.indexOf(estado_evaluado),
                                       estados_finales.indexOf(estado_temporal), 
                                       a));
                   }else{
                       estados_por_evaluar.add(estado_temporal);
                       estados_finales.add(estado_temporal);
                       afd.transciones.add(
                               new Transicion(
                                       estados_finales.indexOf(estado_evaluado),
                                       estados_finales.size()-1, 
                                       a));
                       for(int e: efinalafnd){
                            if (estado_temporal.contains(e)) {
                                afdFinales.add(estados_finales.size()-1);
                            }
                        }
                   }
               }else{
                   estado_antes_cerradura.clear();
               }
            }
            
            if(estados_por_evaluar.isEmpty()){
                bandera = false;
            } 
        }
        afd.alfabeto.addAll(afnd.alfabeto);
        afd.estadosFinales.addAll(afdFinales);
        
        for(int c=0; c < estados_finales.size(); c++){
            afd.estados.add(c);
        }
    }
    
    public static void mostrarAFD(){
        for(Transicion t: afd.transciones)
            System.out.println(t.toString());
    }
    
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            LeerArchivo();
            EjecutarAlgoritmo();
             afd.separarEstados();
            afd.getAutomata();
            System.out.println("Estados finales "+afd.estadosFinales.toString());
            
            System.out.println("Estados " + afd.estados.toString());
            
        } catch (IOException ex) {
            Logger.getLogger(Algoritmo.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
