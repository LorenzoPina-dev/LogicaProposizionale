/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneDati;

import java.util.Map;

/**
 *
 * @author user
 */
public class Nodo {
    private char dato;
    public Nodo sx,dx;

    public Nodo(char dato) {
        this.dato = dato;
        this.sx = null;
        this.dx = null;
    }
    public char getDato(){
        return dato;
    }
    public String toString(){
        String ris="";
        if(sx!=null)
            ris+="("+ sx.toString()+")";
        ris+=dato;
        if(dx!=null)
            ris+="("+ dx.toString()+")";
        return ris;
    }
    public boolean isFoglia(){
        return sx==null&&dx==null;
    }
    public String toStringEssenziale(){
        String ris="";
        if(sx!=null)
            if(Util.isOperatore(sx.dato)&&Util.compareOperatore(dato, sx.dato)==1)
                ris+="("+ sx.toStringEssenziale()+")";
            else
                ris+=sx.toStringEssenziale();
        ris+=dato;
        if(dx!=null)
            if(Util.isOperatore(dx.dato)&&Util.compareOperatore(dato, dx.dato)==1)
                ris+="("+ dx.toStringEssenziale()+")";
            else
                ris+=dx.toStringEssenziale();
        return ris;
    }
    
    public boolean valuta(Map<String,Boolean> valori) throws Exception{
        if(sx==null && dx==null)
        {
            if(valori.containsKey(dato+""))
                return valori.get(dato+"");
            throw new Exception("Non hai dato valori a tutte le figlie");
        }
        switch (dato) {
            case '!':
                return !dx.valuta(valori);
            case '&':
                return sx.valuta(valori)&&dx.valuta(valori);
            case '|':
                return sx.valuta(valori)||dx.valuta(valori);
            case '>':
                return sx.valuta(valori)||!dx.valuta(valori);
            case '<':
                return (sx.valuta(valori)&& dx.valuta(valori))||(!sx.valuta(valori)&& !dx.valuta(valori));
        }
        throw new Exception("non valutabile");
    }
    
}
