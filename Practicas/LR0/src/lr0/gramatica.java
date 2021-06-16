/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lr0;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author emili
 */
public class gramatica implements Serializable{
    public  ArrayList<String> noTerminales = new ArrayList<>();
    public  ArrayList<String> terminales = new ArrayList<>();
    public  ArrayList<ArrayList<String>> producciones = new ArrayList<>();
    public  ArrayList<String> inicios = new ArrayList<>();
    public  ArrayList<ArrayList<String>> derechas = new ArrayList<>();
    public  ArrayList<ArrayList<String>> produccionesPuntosIniciales = new ArrayList<>();
    public  ArrayList<String> iniciosEx = new ArrayList<>();
    private ArrayList<ArrayList<String>> derechasEx = new ArrayList<>();
    public  ArrayList<ArrayList<String>> produccionesEx = new ArrayList<>();  
    
    public void LeerArchivo() throws FileNotFoundException, IOException{
        
        String archivo = "C:\\Users\\emili\\OneDrive - Instituto Politecnico Nacional\\"
                + "Desktop\\Compiladores2021B\\Compiladores2021B\\Practicas\\LR0.csv";
        int nfila = 0;
        BufferedReader bufferLectura = new BufferedReader(
                new FileReader(archivo));
        String linea = bufferLectura.readLine();
  
        while (linea != null) {
         // Sepapar la linea leída con el separador definido previamente
        String[] campos = linea.split(","); 
        if(nfila == 0){
            for(String s : campos){ 
                noTerminales.add(s.trim());
            }
            nfila ++;
        } else if(nfila == 1){
            for(String s : campos){ 
                terminales.add(s.trim());
            }
            nfila++;
        }else {
            ArrayList<String> produccion = new ArrayList<>();
            for(String s : campos){ 
                produccion.add(s.trim());
            }
            producciones.add(produccion);
        }
        linea = bufferLectura.readLine();
        }
        
        for (int i = 0; i < producciones.size(); i++) {
            inicios.add(producciones.get(i).get(0));
            ArrayList<String> p = producciones.get(i);
            ArrayList<String> aux = new ArrayList<>();
            for (int j = 0; j < p.size(); j++) {
                if(j>0){
                    aux.add(p.get(j));
                }
            }
            derechas.add(aux);
        }
        System.out.println("Archivo leido correctamente");
    }
    
    public void extenderGramatica(){
        iniciosEx.add("Z");
        iniciosEx.addAll(inicios);
        ArrayList<String> aux = new ArrayList<>();
        aux.add(".");
        aux.add(inicios.get(1));
        derechasEx.add(aux);
        for(ArrayList<String> p : derechas){
            ArrayList<String> aux2 = new ArrayList<>();
            aux2.add(".");
            aux2.addAll(p);
            derechasEx.add(aux2);
        }
        System.out.println("Gramatica extendida correctamente "+ iniciosEx);
        System.out.println(derechasEx);
    }

    public ArrayList<ArrayList<String>> getDerechasEx() {
        return derechasEx;
    }
    
    
}
