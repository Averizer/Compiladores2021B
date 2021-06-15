/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lr0;

import java.awt.image.Kernel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author emili
 */
public class algoritmo {
    public static gramatica g = new gramatica();
    public static ArrayList<ArrayList<ArrayList<String>>> conjuntos = new ArrayList<>();
    public static ArrayList<ArrayList<ArrayList<String>>> kernels = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static <T> ArrayList<T> eliminarDuplicados(ArrayList<T> list){
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
    
    public static ArrayList<ArrayList<String>> Cerradura(ArrayList<String> evaluando){
        Stack <ArrayList<String>> conjuntosFaltantes = new Stack <ArrayList<String>>();
        conjuntosFaltantes.add(evaluando);
        ArrayList<ArrayList<String>> resultado = new ArrayList<>();
        resultado.add(evaluando);
        boolean bandera = true;
        
        ArrayList<Integer> yaRevisados = new ArrayList<>();
        System.out.println("derechas "+ g.derechasEx);
       while(bandera){
            ArrayList<String> actual = conjuntosFaltantes.pop();
            int indicePunto  = actual.indexOf(".");
            if(indicePunto != actual.size()-1){
                if(g.noTerminales.contains(actual.get(indicePunto+1))){
                    ArrayList<Integer> indices = new ArrayList<>();
                    for (int i = 0; i < g.iniciosEx.size(); i++) {
                        if(g.iniciosEx.get(i).equals(actual.get(indicePunto+1))){
                            indices.add(i);
                        }
                    }
                    if(!indices.isEmpty()){
                        for (int i = 0; i < indices.size(); i++){
                            if(!yaRevisados.contains(indices.get(i))){
                                conjuntosFaltantes.add(g.derechasEx.get(indices.get(i)));
                                resultado.add(g.derechasEx.get(indices.get(i)));
                                yaRevisados.add(indices.get(i));
                            }
                        }
                    }
                    
                }
            } 
            
            if(conjuntosFaltantes.empty())
                bandera = false;
            
        }
        return resultado;
    }
    
    public static ArrayList<ArrayList<String>> mover(ArrayList<ArrayList<String>> actual, String letra){
        ArrayList<ArrayList<String>> resultado = new ArrayList<>();
        for(ArrayList<String> produccion: actual){
            for (int i = 0; i < produccion.size()-1; i++) {
                if(produccion.get(i).equals(".") && produccion.get(i+1).equals(letra)){
                    ArrayList<String> s = produccion;
                    s.remove(i);
                    s.add(i+1,".");
                    resultado.add(s);
                }
            }
        }
        System.out.println("Res "+resultado);
        System.out.println("DEX "+ g.derechasEx);
        return resultado;
    }
    
    public static void ejecutarAlgoritmo(){
        Stack <ArrayList<ArrayList<String>>> conjuntosFaltantes = new Stack <ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<String>> tablaNoTerminales = new ArrayList<>();
        /*Paso 2 cerradura del estado 0*/
        
        System.out.println("\nCerradura paso 2");
        ArrayList<ArrayList<String>> aux = Cerradura(g.derechasEx.get(0));
        conjuntosFaltantes.add(aux);
        boolean bandera = true;
        conjuntos.add(aux);
        
        
        while(bandera){
            //Revisar que letras tengo que mover 
            ArrayList<String> letras = new ArrayList<>();
            ArrayList<ArrayList<String>> actual = conjuntosFaltantes.pop();
            
            for(int i = 0; i < actual.size(); i++) {
                int indicePunto = actual.get(i).indexOf(".");
                if(indicePunto != actual.size()-1){
                    letras.add(actual.get(i).get(indicePunto+1));
                }
            } 
            bandera = false;
            eliminarDuplicados(letras); //Eliminar letras repetidas
            System.out.println("Letras "+ letras);
            
            for(String s : letras){   //Para cada letra ejecutar mover, con el conjunto actual
                System.out.println("-------------INICIO LETRA EVALUADA-----------");
                ArrayList<ArrayList<String>> nuevoConjunto = new ArrayList<>();
                ArrayList<ArrayList<String>> kernelActual = mover(actual, s);   //Ejecutar mover
                nuevoConjunto.addAll(kernelActual);
                if(kernels.contains(kernelActual)){
                    System.out.println("Kernel repetido");
                }else{
                    for(ArrayList<String> conjunto: kernelActual){
                        ArrayList<ArrayList<String>> despuesCerradura = Cerradura(conjunto);
                        nuevoConjunto.addAll(despuesCerradura);
                    }
                }
                eliminarDuplicados(nuevoConjunto);
                System.out.println("Nuevo conjunto "+nuevoConjunto);
                
                System.out.println("-------------FIN LETRA EVALUADA-----------");
            }
            
            if(conjuntosFaltantes.isEmpty()){
                bandera = false;
            } 
        }
        
    }
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        g.LeerArchivo();
        g.extenderGramatica();
        System.out.println("Iniciales -> "+g.iniciosEx);
        System.out.println("Producciones -> "+g.derechasEx);
        ejecutarAlgoritmo();
    }
    
}
