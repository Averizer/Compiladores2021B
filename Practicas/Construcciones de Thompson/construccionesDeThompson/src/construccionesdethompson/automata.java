 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construccionesdethompson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class automata {
        ArrayList<String> alfabeto = new ArrayList<>();
        public ArrayList<Integer> estados;
        public ArrayList<Transicion> transciones;
        public int estadoFinal;
        public ArrayList<String> estdosFinales = new ArrayList<>();
        public ArrayList<Integer> estadosFinalesint = new ArrayList<>();
        
        public automata(){
            this.estados = new ArrayList<Integer>();
            this.transciones = new ArrayList<Transicion>();
            this.estadoFinal = 0;
        }
        public automata(int tam){
            this.estados = new ArrayList<Integer>();
            this.transciones = new ArrayList<Transicion>();
            this.estadoFinal = 0;
            this.setTamEstados(tam); 
       }
        public automata(char c){
            this.estados = new ArrayList<Integer>();
            this.transciones = new ArrayList<Transicion>();
            this.setTamEstados(2);
            this.estadoFinal = 1;
            this.transciones.add(new Transicion(0,1,c));      
        }
        
        public void setTamEstados(int size){
            for(int i=0; i < size; i++){
                this.estados.add(i);
            }
        }
        
        public void getAutomata(String inputString) throws FileNotFoundException{
            PrintWriter pw = new PrintWriter(new File("C:\\Users\\emili\\OneDrive - Instituto Politecnico Nacional" +
                    "\\Desktop\\Compiladores\\Compiladores\\Practicas\\AFND.csv"));
            evaluarAlfabeto(inputString);
            String aux = "";
            pw.write(alfabeto.toString().replace("[", "").replace("]", "")+"\n");

            for (Transicion t : transciones ){
                estadosFinalesint.add(t.edo_final);
            }
            pw.write(Collections.max(estadosFinalesint).toString());
            for (Transicion t : transciones ){
                aux = t.edo_inicial + "," + t.simbolo + "," +t.edo_final+"\n";
                System.out.println(aux);
                pw.write(aux);
            }
            pw.close();
            
            System.out.println("Estado final:" + Collections.max(estadosFinalesint));
        }
        
        public void evaluarAlfabeto(String inputString){
        
            // Mapa Hash con caracter como llave, cuenta como valor
            HashMap<Character, Integer> charCountMap
                = new HashMap<Character, Integer>();

            // String to charArrat

            char[] strArray = inputString.toCharArray();

            // Checar cada caracteri de arreglo de caracteres
            for (char c : strArray) {
                if (charCountMap.containsKey(c)) {

                    // Si está el caracter sumarle 1 a la cuenta
                    charCountMap.put(c, charCountMap.get(c) + 1);
                }
                else {

                    // Si no está se agrega y se le pone 1 en la cuenta
                    charCountMap.put(c, 1);
                }
            }

            // Mostrar el mapa (Para cada entrada en el mapa)
            // (Iterar sobre las entradas, mostrar llave y valor del conjunto de entradas)
            for (Map.Entry entry : charCountMap.entrySet()) {
                System.out.println(entry.getKey() + "" + entry.getValue());
                alfabeto.add(entry.getKey().toString());
            }
            // Limpiar alfabeto
            alfabeto.remove("(");
            alfabeto.remove(")");
            alfabeto.remove("|");
            alfabeto.remove("*");

            System.out.println(alfabeto);


        }
}
