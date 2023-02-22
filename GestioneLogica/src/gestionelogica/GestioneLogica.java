/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestionelogica;

import GestioneDati.Tree;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author user
 */
public class GestioneLogica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("not= ! ");
        System.out.println("and= & ");
        System.out.println("or= | ");
        System.out.println("implica= > ");
        System.out.println("doppia implicazione= < ");
        System.out.println("Scrivi la proposizione da analizzare");
        Scanner s=new Scanner(System.in);
        Tree t=Tree.Genera(s.nextLine());
        System.out.println("La proposizione con tutte le tonde è:");
        System.out.println(t.toString());
        System.out.println("");
        System.out.println("La proposizione con il minimo numero si tonde è:");
        System.out.println(t.toStringEssenziale());
        System.out.println("");
        System.out.println("La tavola di verita è:");
        String[][] tavola=t.getTabellaVerita();
        for(int i=0;i<tavola.length;i++)
        {
            for(int j=0;j<tavola[i].length;j++)
                System.out.print(tavola[i][j]+" ");
            System.out.println("");
        }
        System.out.println("");
        System.out.println("Il tableaux vero è:");
        System.out.println(t.Tableaux(true));
        System.out.println("");
        System.out.println("Il tableaux falso è:");
        System.out.println(t.Tableaux(false));
        System.out.println("");
    }
    
}
