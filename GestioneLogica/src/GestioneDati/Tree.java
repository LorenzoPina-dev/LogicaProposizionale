package GestioneDati;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Tree {

    Nodo head;

    public Tree() {
        this.head = null;
    }
    public Tree(Nodo head) {
        this.head = head;
    }
    
    public static Tree Genera(String formula)
    {
        Stack<String> coda=new Stack();
        coda.add("-"+formula);
        Tree ris=new Tree();
        while(coda.size()>0)
        {
            String[] daElaborare=coda.pop().split("-");
            if(daElaborare.length<2)
                continue;
            if(daElaborare[1].charAt(0)=='(')
                daElaborare[1]=daElaborare[1].substring(1);
            if(daElaborare[1].charAt(daElaborare[1].length()-1)==')')
                daElaborare[1]=daElaborare[1].substring(0,daElaborare[1].length()-1);
            
            int operatorePeggiore=Util.getOperatorePeggiore(daElaborare[1]);
            if(operatorePeggiore!=-1)
            {
                coda.add(daElaborare[0]+"S-"+daElaborare[1].substring(0,operatorePeggiore));
                coda.add(daElaborare[0]+"D-"+daElaborare[1].substring(operatorePeggiore+1,daElaborare[1].length()));
                ris.add(daElaborare[0],daElaborare[1].charAt(operatorePeggiore));
            }
            else if(!daElaborare[1].equals(""))
                ris.add(daElaborare[0],daElaborare[1].charAt(0));
        }
        
        
        return ris;
    }
    
    public Set<Character> getLettere(){//restituisco le foglie
        Set<Character> ris=new HashSet();
        Stack<Nodo> stack=new Stack();
        stack.add(head);
        while(!stack.isEmpty())
        {
            Nodo n=stack.pop();
            if(n.isFoglia())
                ris.add(n.getDato());
            if(n.dx!=null)
                stack.add(n.dx);
            if(n.sx!=null)
                stack.add(n.sx);
        }
        return ris;
    }
    public List<Nodo> getNodiIntermedi(){
        
        Set<Nodo> set=new HashSet();
        List<Nodo> ris=new ArrayList();
        Stack<Nodo> stack=new Stack<>();
        stack.add(head);
        while(!stack.isEmpty())
        {
            Nodo n=stack.pop();
            if(n.dx!=null|| n.sx!=null)
                if(!set.contains(n))
                {
                    set.add(n);
                    ris.add(n);
                }
            if(n.sx!=null)
                stack.add(n.sx);
            if(n.dx!=null)
                stack.add(n.dx);
        }
        return ris;
    }
    public String[][] getTabellaVerita(){
        Object[] lettere=getLettere().toArray();
        List<Nodo> sottoFormule=getNodiIntermedi();
        String[][] ris=new String[(int)Math.pow(2, lettere.length)+1][lettere.length+sottoFormule.size()];
        for(int i=0;i<lettere.length;i++)
            ris[0][i]=((char)lettere[i])+"";
        for(int i=0;i<sottoFormule.size();i++)
            ris[0][lettere.length+i]=(sottoFormule.get(sottoFormule.size()-1-i)).toStringEssenziale();
        for(int i=0;i<lettere.length;i++)//creo le combinazioni possibili
        {
            int partenza=0;
            boolean daInserire=true;
            while(partenza<(int)Math.pow(2, lettere.length))
            {
                if(partenza%(int)Math.pow(2, lettere.length-i-1)==0)
                    daInserire=!daInserire;
                if(daInserire)
                    ris[partenza+1][i]="1";
                else
                    ris[partenza+1][i]="0";
                partenza++;
            }
        }
        for(int i=0;i<(int)Math.pow(2, lettere.length);i++) //valuto ogni possibilità
            for(int j=0;j<sottoFormule.size();j++)
            {
            try {
                Map<String,Boolean> valori=new HashMap<>();
                for(int x=0;x<lettere.length;x++)
                    valori.put(ris[0][x], ris[i+1][x].charAt(0)=='1');
                ris[i+1][lettere.length+j]=(new Tree(sottoFormule.get(sottoFormule.size()-1-j))).valutaDinamica(valori)?"1":"0";
            } catch (Exception ex) {
                Logger.getLogger(Tree.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        return ris;
    }
    public boolean valutaDinamica(Map<String,Boolean> valori){
        Map<Nodo,Boolean> risultati=new HashMap<>();
        Stack<Nodo> stack=new Stack<>();
        stack.add(head);
        while(!stack.isEmpty())
        {
           Nodo n=stack.peek();
           
            if(n.isFoglia())
            {
                stack.pop();
                risultati.put(n,valori.get(n.getDato()+""));
            }
            else if(n.sx==null && risultati.containsKey(n.dx))
            {
                stack.pop();
                risultati.put(n,Util.EseguiValutazione(n.getDato(),null,risultati.get(n.dx)));
            }
            else if(n.dx==null && risultati.containsKey(n.sx))
            {
                stack.pop();
                risultati.put(n,Util.EseguiValutazione(n.getDato(),risultati.get(n.dx),null));
            }
            else  if(risultati.containsKey(n.dx)&&risultati.containsKey(n.sx) )
            {
                stack.pop();
                risultati.put(n,Util.EseguiValutazione(n.getDato(),risultati.get(n.sx),risultati.get(n.dx)));
            }
            else
            {
                if(n.dx!=null)
                    stack.add(n.dx);
                if(n.sx!=null)
                    stack.add(n.sx);
            }
        }
        return risultati.get(head);
    }
    public boolean valuta(Map<String,Boolean> valori)
    {
        try {
            return head.valuta(valori);
        } catch (Exception ex) {
            Logger.getLogger(Tree.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public  void add(String percorso, char dato) {
        Nodo n=head;
        if(percorso.equals(""))
            head=new Nodo(dato);
        else
        {
            for(int i=0;i<percorso.length()-1;i++)
                if(percorso.charAt(i)=='S')
                    n=n.sx;
                else
                    n=n.dx;
            if(percorso.charAt(percorso.length()-1)=='S')
                n.sx=new Nodo(dato);
            else
                n.dx=new Nodo(dato);
        }
    }
    public String Tableaux(boolean inizio){
        String ris="";
        Stack<List<Object[]>> rami=new Stack<>();
        rami.add(new ArrayList());
        Object[] temp=new Object[2];
        temp[0]=inizio;
        temp[1]=head;
        rami.get(0).add(temp);
        while(!rami.isEmpty()){
            String riga="";
            for(List<Object[]> l:rami)//per stampare i passaggi intermedi
            {
                for(Object[] n:l)
                    riga+=(((boolean)n[0])?"V ":"F ")+((Nodo)n[1]).toStringEssenziale()+",";
                riga=riga.substring(0,riga.length()-1)+"  |  ";
            }
            
            ris+=riga.substring(0,riga.length()-5)+"\n";
            boolean continua=true;
            do{
                
                List<Object[]> daFare=rami.pop();
                int migliore=Util.getOperatoreMiglioreTableaux(daFare);
                if(migliore==-1)
                    break;
                List<Object[]> getRisultato=Util.AppllicaOperatoreTableaux(daFare.get(migliore));
                if(Util.divide(daFare.get(migliore)))
                {
                    List<Object[]> partizione=new ArrayList(),partizione2=new ArrayList();
                    for(int i=0;i<migliore;i++)
                    {
                        partizione.add(daFare.get(i));
                        partizione2.add(daFare.get(i));
                    }
                    for(int i=0;i<getRisultato.size();i++)
                        if(i<getRisultato.size()/2)
                            partizione.add(getRisultato.get(i));
                    else
                            partizione2.add(getRisultato.get(i));

                    for(int i=migliore+1;i<daFare.size();i++)
                    {
                        partizione.add(daFare.get(i));
                        partizione2.add(daFare.get(i));
                    }
                    rami.add(partizione2);
                    rami.add(partizione);
                }
                else
                {
                    List<Object[]> partizione=new ArrayList();
                    for(int i=0;i<migliore;i++)
                        partizione.add(daFare.get(i));
                    partizione.addAll(getRisultato);
                    for(int i=migliore+1;i<daFare.size();i++)
                        partizione.add(daFare.get(i));
                    rami.add(partizione);
                }
            }while(!continua);
        }
        return ris;
    }
    
    public String toStringEssenziale(){
        return head.toStringEssenziale();
            
    }
    public String toString(){
        return head.toString();
            
    }
    
}
