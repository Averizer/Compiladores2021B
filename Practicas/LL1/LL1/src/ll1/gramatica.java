/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ll1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author emili
 */
public class gramatica {
    public  ArrayList<String> noTerminales = new ArrayList<>();
    public  ArrayList<String> terminales = new ArrayList<>();
    public  ArrayList<ArrayList<String>> producciones = new ArrayList<>();
    public  ArrayList<String> inicios = new ArrayList<>();
    public  ArrayList<ArrayList<String>> alfas = new ArrayList<>();
    
    public void LeerArchivo() throws FileNotFoundException, IOException{
        
        String archivo = "C:\\Users\\emili\\OneDrive - Instituto Politecnico Nacional\\"
                + "Desktop\\Compiladores2021B\\Compiladores2021B\\Practicas\\LL1.csv";
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
        
        System.out.println("No terminales "+ noTerminales);
        System.out.println("Terminales "+terminales);
        System.out.println("Producciones "+ producciones);
        for (int i = 0; i < producciones.size(); i++) {
            inicios.add(producciones.get(i).get(0));
            ArrayList<String> p = producciones.get(i);
            ArrayList<String> aux = new ArrayList<>();
            for (int j = 0; j < p.size(); j++) {
                if(j>0){
                    aux.add(p.get(j));
                }
            }
            alfas.add(aux);
        }
        System.out.println("Inicios "+ inicios);
        System.out.println("Alfas "+alfas.toString());
    }
}
