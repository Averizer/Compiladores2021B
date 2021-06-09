/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmodeminimizacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author emili
 */
public class AlgoritmoDeMinimizacion {
    public static ArrayList<ArrayList<Integer> > conjuntos    = new ArrayList<>();
    public static ArrayList<String> alfabeto        = new ArrayList<>();
    
    public static automata afd = new automata();
    public static automata afdMin = new automata();
    
    public static void LeerArchivo() throws FileNotFoundException, IOException{
        ArrayList<Integer> finales    = new ArrayList<>();
        ArrayList<Integer> noFinales    = new ArrayList<>();
        String archivo = "C:\\Users\\emili\\OneDrive - Instituto Politecnico Nacional"
                + "\\Desktop\\Compiladores\\Compiladores\\Practicas\\AFD.csv";
        int nfila = 0;
        BufferedReader bufferLectura = new BufferedReader(
                new FileReader(archivo));
        String linea = bufferLectura.readLine();
  
        while (linea != null) {
         // Sepapar la linea leída con el separador definido previamente
        String[] campos = linea.split(","); 
        if(nfila == 0){
             for(String s : campos){ 
                afd.alfabeto.add(""+s.trim());
            }
            nfila ++;
        } else if(nfila == 1){
            for(String s : campos){ 
                finales.add(Integer.parseInt(s));
            }
            conjuntos.add(finales);
            nfila++;
        }else if(nfila == 2){
            for(String s : campos){ 
                noFinales.add(Integer.parseInt(s));
            }
            conjuntos.add(noFinales);
            nfila++;
        }else {
            Transicion t = new Transicion(Integer.parseInt(campos[0]), //Einicial
                     Integer.parseInt(campos[2]), //E final
                     campos[1]); // Estado
            afd.transciones.add(t);
        }
        linea = bufferLectura.readLine();
        }
        System.out.println("Alfabeto: "+ afd.alfabeto.toString());
        System.out.println("Conjuntos: "+ conjuntos.toString());
    }
    
    public void calcularDiferenciacion(){
    
    }
    
    public static void ejecutarAlgoritmo(){
        boolean bandera = true;
        boolean diferenciable = false;
        Queue <ArrayList<Integer>> estados_por_evaluar 
                = new LinkedList<ArrayList<Integer>>() ;
        estados_por_evaluar.addAll(conjuntos);
        
        while (bandera){
            ArrayList<Integer> evaluando    = new ArrayList<>();
            evaluando = estados_por_evaluar.poll();
            System.out.println("Ev "+evaluando.toString());
            ArrayList<Integer> conjunto1 = new ArrayList<>();
            ArrayList<Integer> conjunto2 = new ArrayList<>();
            
            if(evaluando.size() > 1){
                System.out.println("Comparacion");
                comparacion: 
                for(int i = 0; i < evaluando.size(); i++){     //Matriz
                    System.out.println("--------------------------");
                    for(int j = 0; j < evaluando.size(); j++){ //Matriz
                        System.out.println("++++++++++++++++++++++");
                        System.out.println("i"+i+"j"+j);
                        conjunto1.clear();
                        conjunto2.clear();
                        if(i < j){      //Matriz matriz superior
                            if(i != j){ //Si no es el mismo estado
                                for(String l: afd.alfabeto){
                                    for(Transicion t: afd.transciones){//Revisar
                                        if((evaluando.get(i) == t.edo_inicial)
                                            &&(t.simbolo.equals(l))){
                                            for(int c1=0;c1<conjuntos.size();c1++){
                                                if(conjuntos.get(c1).
                                                   contains(t.edo_final)){
                                                    System.out.println(t.edo_inicial
                                                    + t.simbolo +") " +  t.edo_final+" "+ c1);
                                                    conjunto1.add(c1);
                                                }
                                            }
                                        }
                                        if((evaluando.get(j)==t.edo_inicial)
                                            &&(t.simbolo.equals(l))){
                                            for(int c2=0;c2<conjuntos.size();c2++){
                                                if(conjuntos.get(c2).
                                                   contains(t.edo_final)){
                                                    System.out.println(t.edo_inicial
                                                    + t.simbolo +") " +  t.edo_final+" "+ c2);
                                                    conjunto2.add(c2);
                                                }
                                            } 
                                        }
                                    }
                                }
                                System.out.println(conjunto1.toString());
                                System.out.println(conjunto2.toString());
                                    /*if(!Arrays.equals(conjunto1.toArray(), 
                                    conjunto2.toArray())){
                                        System.out.println("Diferenciables");
                                    }*/
                            }
                        }
                    }
                }
            }
            
            
            
            if(estados_por_evaluar.isEmpty()){
               bandera = false; 
            }
        }
    }
           
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        LeerArchivo();
        ejecutarAlgoritmo();
        
    }
    
}
