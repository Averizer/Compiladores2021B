 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmodelossubconjuntos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class automata {
        ArrayList<String> alfabeto = new ArrayList<>();
        public ArrayList<Integer> estados;
        public ArrayList<Transicion> transciones;
        public  ArrayList<Integer> estadosFinales = new ArrayList<>();
        public int estadoFinal;
        
        public automata(){
            this.estados = new ArrayList<Integer>();
            this.transciones = new ArrayList<Transicion>();
            this.estadoFinal = 0;
        }

        public void getAutomata() throws FileNotFoundException{
            PrintWriter pw = new PrintWriter(new File("C:\\Users\\emili\\OneDrive - Instituto Politecnico Nacional\\Desktop"
                    + "\\Compiladores2021B\\Compiladores2021B\\Practicas\\AFD.csv"));
            
            String aux = "";
            pw.write(alfabeto.toString().replace("[", "").replace("]", "")+"\n");
            pw.write(estadosFinales.toString().replace("[", "").replace("]", "").trim()+"\n");
            pw.write(estados.toString().replace("[", "").replace("]", "").trim()+"\n");
            for (Transicion t : transciones ){
                aux = t.edo_inicial + "," + t.simbolo + "," +t.edo_final+"\n";
                System.out.println(aux);
                pw.write(aux);
            }
            pw.close();
        }
        
        public void separarEstados (){
            estados.removeAll(estadosFinales);
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
