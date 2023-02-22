/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestioneDati;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author user
 */
public class Util {
    public static boolean isOperatore(char carattere)
    {
        switch(carattere)
            {
                case '!':
                case '&':
                case '|':
                case '>':
                case '<':
                    return true;
            }
        return false;
    }
    public static int getPosOperatore(String proposizione,int posizione)
    {
        for(int i=posizione;i<proposizione.length();i++)
            if(isOperatore(proposizione.charAt(i)))
                return i;
        return -1;
    }
    public static int compareOperatore(char operatore1,char operatore2){
        if(operatore1=='!')
            return 1;
        if(operatore1=='<')
            return -1;
        if(operatore1=='&')
            if(operatore2=='!')
                return -1;
            else
                return 1;
        if(operatore1=='|')
            if(operatore2=='!'||operatore2=='&')
                return -1;
            else
                return 1;
        if(operatore1=='>')
            if(operatore2=='<')
                return 1;
            else
                return -1;
        return 0;
    }
    public static int getOperatorePeggiore(String formula)
    {
        int parentesi=0;
        int operatorePeggiore=-1;
        int parentesiOperatore=0;
        for(int i=0;i<formula.length();i++)
        {
            if(formula.charAt(i)=='(')
                parentesi++;
            else if(formula.charAt(i)==')')
                parentesi--;
            else if(isOperatore(formula.charAt(i)))
                if(operatorePeggiore==-1 || ( compareOperatore(formula.charAt(operatorePeggiore), formula.charAt(i))==1&&parentesiOperatore>=parentesi))
                {
                    operatorePeggiore=i;
                    parentesiOperatore=parentesi;
                }
        }
        return operatorePeggiore;
        /*
        //senza tonde
        int operatorePeggiore=-1;
        int posTemp=0;
        while(posTemp<formula.length())
        {
            int next= getPosOperatore(formula, posTemp);
            if(next!=-1&&(operatorePeggiore==-1 || compareOperatore(formula.charAt(operatorePeggiore), formula.charAt(next))==1))
            {
                operatorePeggiore=next;
                posTemp=next+1;
            }
            else
                posTemp++;
        }
        return operatorePeggiore;
    */
    }
    

    static Boolean EseguiValutazione(char operatore,Boolean sx,Boolean dx) {
        
        switch (operatore) {
            case '!':
                return !dx;
            case '&':
                return sx&&dx;
            case '|':
                return sx||dx;
            case '>':
                return !sx||dx;
            case '<':
                return (sx&& dx)||(!sx&& !dx);
        }
        return false;
    }

    static int getOperatoreMiglioreTableaux(List<Object[]> rami) {
        boolean divide=false;
        int ris=-1;
        for(int j=0;j<rami.size();j++)
        {
            if(!isOperatore(((Nodo)rami.get(j)[1]).getDato()))
                continue;
            if(!divide(rami.get(j)))
                return j;
            if(ris==-1)
                ris=j;
        }
        return ris;
    }

    static List<Object[]> AppllicaOperatoreTableaux(Object[] daFare) {
        List<Object[]> ris=new ArrayList<>();
        Nodo n=(Nodo) daFare[1];
        Object[] temp;
        switch (n.getDato()) {
            case '!':
                temp=new Object[2];
                temp[0]=!(boolean)daFare[0];
                temp[1]=n.dx;
                ris.add(temp);
                break;
            case '&':
                temp=new Object[2];
                temp[0]=(boolean)daFare[0];
                temp[1]=n.sx;
                ris.add(temp);
                temp=new Object[2];
                temp[0]=(boolean)daFare[0];
                temp[1]=n.dx;
                ris.add(temp);
                break;
            case '|':
                temp=new Object[2];
                temp[0]=(boolean)daFare[0];
                temp[1]=n.sx;
                ris.add(temp);
                temp=new Object[2];
                temp[0]=(boolean)daFare[0];
                temp[1]=n.dx;
                ris.add(temp);
                break;
            case '>':
                temp=new Object[2];
                temp[0]=!(boolean)daFare[0];
                temp[1]=n.sx;
                ris.add(temp);
                temp=new Object[2];
                temp[0]=(boolean)daFare[0];
                temp[1]=n.dx;
                ris.add(temp);
                break;
            case '<':
                if((boolean)daFare[0])
                {
                    temp=new Object[2];
                    temp[0]=true;
                    temp[1]=n.sx;
                    ris.add(temp);
                    temp=new Object[2];
                    temp[0]=true;
                    temp[1]=n.dx;
                    ris.add(temp);
                    temp=new Object[2];
                    temp[0]=false;
                    temp[1]=n.sx;
                    ris.add(temp);
                    temp=new Object[2];
                    temp[0]=false;
                    temp[1]=n.dx;
                    ris.add(temp);
                }
                else
                {
                    temp=new Object[2];
                    temp[0]=true;
                    temp[1]=n.sx;
                    ris.add(temp);
                    temp=new Object[2];
                    temp[0]=false;
                    temp[1]=n.dx;
                    ris.add(temp);
                    temp=new Object[2];
                    temp[0]=false;
                    temp[1]=n.sx;
                    ris.add(temp);
                    temp=new Object[2];
                    temp[0]=true;
                    temp[1]=n.dx;
                    ris.add(temp);
                }
                break;
        }
        return ris;
    }

    public static boolean divide(Object[] get) {
        Nodo n=(Nodo) get[1];
        boolean ris=false;
        switch (n.getDato()) {
            case '&':
                ris=!(Boolean)get[0];
                break;
            case '|':
                ris=(Boolean)get[0];
                break;
            case '>':
                ris=(Boolean)get[0];
                break;
            case '<':
                ris=true;
                break;
        }
        return ris;
    }
}
