/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ll1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author emili
 */
public class algoritmo {
    public static gramatica g = new gramatica();
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
    
    public static void ejecutarAlgoritmo() throws IOException{
        g.LeerArchivo();
        int[][] tabla = new int[g.terminales.size()][g.noTerminales.size()+1];
        //Inicializar tabla
        for (int i = 0; i < g.terminales.size(); i++) {
            for (int j = 0; j < g.noTerminales.size()+1; j++) {
                tabla[i][j]= -1;
                
            }
        }
        
        HashMap<String, ArrayList<String>> calculos 
                = new HashMap<String, ArrayList<String>>();
        // calcular primero de alfa para todas las producciones
        for(int i = 0 ; i < g.alfas.size(); i++){
            System.out.println("________________Nuevo Alfa__________________");
            if(g.alfas.get(i).get(0).equals("E")){
                System.out.println("S("+g.inicios.get(i)+")");
                ArrayList<String> aux = eliminarDuplicados(s(g.inicios.get(i)));
                calculos.put("s("+g.inicios.get(i)+")", aux);
                int x =  g.noTerminales.indexOf(g.inicios.get(i)) ;
                for(String t: aux){
                    int y;
                    if(t.contains("$")){
                        y = g.terminales.size();
                    } else {
                        y = g.terminales.indexOf(t);
                    }
                    
                    tabla[x][y] = i;
                }
                
                System.out.println("----------------->"+calculos);
            }else{
                System.out.println("P("+g.alfas.get(i)+")");
                ArrayList<String> aux = eliminarDuplicados(p(g.alfas.get(i).get(0)));
                calculos.put("p("+g.alfas.get(i).get(0)+")", aux);
                System.out.println(calculos);
                int x =  g.noTerminales.indexOf(g.inicios.get(i)) ;
                System.out.println(g.inicios.get(i));
                System.out.println(g.noTerminales);
                System.out.println("x "+ x);
                for(String t: aux){
                    int y;
                    if(t.contains("$")){
                        y = g.terminales.size();
                    } else {
                        y = g.terminales.indexOf(t);
                    }
                    
                    tabla[x][y] = i;
                }
            }
        }
        System.out.println(calculos.toString());
        System.out.println("Tabla");
        for(String nt : g.terminales){
            System.out.print("\t "+nt);
        }
        System.out.print("\t $\n");
        for (int i = 0; i < g.terminales.size(); i++) {
            System.out.print(g.noTerminales.get(i));
            for (int j = 0; j < g.noTerminales.size()+1; j++) {
                System.out.print("\t["+tabla[i][j]+"]");
                
            }
            System.out.println("");
        }
        
        
        
    }
    
    public static ArrayList<String> p(String simb){
        System.out.println("==>P<==");
        ArrayList<String> res = new ArrayList<>();
        
        if(g.terminales.contains(simb)){ //Regla 1, es terminal
            res.add(simb);
            System.out.println("Terminal encontrado -> " + simb);
        }else if(g.noTerminales.contains(simb)){ //Regla 2, no terminal
            ArrayList<Integer> noTerminalesAEvaluar = new ArrayList<>();
            //ver inicios con ese no terminal 
            System.out.println("No terminal encontrado "+simb);
            for(int i = 0 ; i < g.inicios.size(); i++){
                if(g.inicios.get(i).equals(simb))
                    noTerminalesAEvaluar.add(i);
            }
            System.out.println(simb + " Se encuentra en"+ noTerminalesAEvaluar);
            for(Integer nt: noTerminalesAEvaluar){
                res.addAll(p(g.alfas.get(nt).get(0)));
            }
        }else if(simb.equals("E")){ // Regla 3, épsilon
            res.add("E");
        }
        return res;
    }
    
    public static ArrayList<String> s(String simb){
        System.out.println("___S___");
        String actual = "S("+simb+")" ;
        ArrayList<String> res = new ArrayList<>();
        //Buscar donde aparece el no terminal
        if (g.inicios.get(0).equals(simb)) {
                    res.add("$");
        }
        
        for (int i = 0; i < g.alfas.size(); i++) {
            if(g.alfas.get(i).contains(simb)){
                ArrayList<String> evaluando = g.alfas.get(i);
                String posibleInicio = g.inicios.get(g.alfas.indexOf(evaluando));
                System.out.println("alfa evaluado "+posibleInicio +" ->"+ evaluando);
                
                if(!evaluando.get(evaluando.size()-1).equals(simb)){ // Regla 2, N esta en medio a
                    System.out.println("R2");
                    res.addAll(p(evaluando.get(evaluando.indexOf(simb)+1)));
                    if(res.contains("E")){
                        res.remove("E");
                        res.addAll(s(evaluando.get(evaluando.indexOf(simb)+1)));
                    }
                }
                if(evaluando.get(evaluando.size()-1).equals(simb)){ // Regla 3, N esta al fina
                    
                    String siguiente = "S("+g.inicios.get(i)+")";
                    System.out.println("R3");
                    
                            
                    if(!actual.equals(siguiente)){ //Evitar que se cicle si se va a calcular lo mismo que buscamos
                        System.out.println("S("+g.inicios.get(i)+")");
                        res.addAll(s(posibleInicio));
                    }else System.out.println("evitado");
                }
            }
        }
        return res;
    }
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ejecutarAlgoritmo();
    }
    
}
