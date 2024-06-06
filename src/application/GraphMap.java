package application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class GraphMap<T,E> {
    class SommetPrinc {
        private int id;
        private T valeur;
        private int couleur=-1;

        public SommetPrinc(int id, T valeur) {
            this.id = id;
            this.valeur = valeur;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id=id;
        }

        public T getVal() {
            return this.valeur;
        }

        public void setVal(T val) {
            this.valeur= val;
        }

        public int getCoul() {
            return this.couleur;
        }

        public void setCoul(int c) {
            this.couleur=c;
        }

        
        public String toString() {
            return "Id : "+id+", Valeur : "+valeur.toString()+", " + "Couleur : "+this.couleur ;
        }
    }
    
    class SommetAdj {
        private int id;
        private E valeur;
        
        public SommetAdj(int id, E valeur) {
            this.id = id;
            this.valeur = valeur;
        }
        
        public int getId() {
            return this.id;
        }
        
        public E getValeur() {
            return this.valeur;
        }
        
        public String toString() {
            return " IdAdj : "+this.id+", ValeurArête : "+ valeur.toString();
        }
    }
    
    private Map<SommetPrinc, List<SommetAdj>> map;
    private int id;
    private int nbCouleurs;
    
    public GraphMap(int k) {
        this.id=0;
        this.nbCouleurs=k;
        map = new HashMap<>();
    }
    
    public SommetPrinc findKeyVal(T val) {
        SommetPrinc sp = null;
        Set<SommetPrinc> s = map.keySet();
        Object[] tabS = s.toArray();
        int i=0;
        while (i<tabS.length&&!((SommetPrinc)tabS[i]).getVal().equals(val))
            {i++;}
        if (i<tabS.length) sp=(SommetPrinc)tabS[i];
        return sp;
    }
    
    private SommetPrinc findKeyId(int id) {
        SommetPrinc sp = null;
        Set<SommetPrinc> s = map.keySet();
        Object[] tabS = s.toArray();
        int i=0;

        while (i<tabS.length && ((SommetPrinc)tabS[i]).getId()!=id)
        {i++;}

        if (i<tabS.length) sp=(SommetPrinc)tabS[i];
        return sp;
    }
    
    public SommetPrinc addNode(T val) { 
        SommetPrinc spr = null;
        if (this.findKeyVal(val)==null) {
            spr = new SommetPrinc(this.id++,val);
            map.put(spr, new LinkedList<>());
        }
        return spr;
    }
    
    public void addEdge(T val1, T val2, E valArête) {
        SommetPrinc spr1=this.findKeyVal(val1);
        SommetPrinc spr2=this.findKeyVal(val2);
        
        if (spr1==null) spr1=addNode(val1);
        
        if (spr2==null) spr2=addNode(val2);
        
        if (!hasEdge(val1,val2)) {
            map.get(spr1).add(new SommetAdj(spr2.getId(),valArête));
            map.get(spr2).add(new SommetAdj(spr1.getId(),valArête));
        }
        else System.out.println("There's an edge between "+val1.toString()+ " and "+val2.toString()+" ");
    }
    
    public boolean hasEdge (int id1, int id2) {
        boolean b = false;
        List<SommetAdj> lsa = map.get(this.findKeyId(id1));
        int i=0;
        
        while (i<lsa.size() && lsa.get(i).getId()!=id2) {
            i++;
        }
        
        if (i<lsa.size()) b = true;
        
        return b;
    }
    
    public boolean hasEdge (T val1, T val2) {
        boolean b = false;
        List<SommetAdj> lsa = map.get(this.findKeyVal(val1));
        SommetPrinc sp2 = this.findKeyVal(val2);
        int id2 = sp2.getId();
        int i=0;
        
        while (i<lsa.size() && lsa.get(i).getId()!=id2) {
            i++;
        }
        
        if (i<lsa.size()) b = true;
        
        return b;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        for (SommetPrinc sp : map.keySet()) {
            buffer.append(sp.toString()).append('\n').append("Voisins :").append('\n');
            for (SommetAdj sa : map.get(sp)) {
                buffer.append(sa.toString()).append('\n');
            }
        
            buffer.append("\n\n");
        }
        return (buffer.toString());
    }
    
    //Colore les noeuds (en débutant par la coul. 0) et retourne le rés.
    public boolean greedyColoring() {
        boolean b = true;
        boolean[] coul_attrib = new boolean[this.nbCouleurs];
        
        for (int i=0; i<this.nbCouleurs; i++) coul_attrib[i] = false;
        Iterator<Map.Entry<SommetPrinc, List<SommetAdj>>> iterator = map.entrySet().iterator();
        
        while (iterator.hasNext() && b) {
            Map.Entry<SommetPrinc, List<SommetAdj>> cour = iterator.next();
            //Indiquer que les coul. des noeuds adjacents sont attribuées
            List<SommetAdj> lsa=cour.getValue();
            
            for (SommetAdj sa : lsa) {
                if (findKeyId(sa.getId()).getCoul()!=-1)
                    coul_attrib[findKeyId(sa.getId()).getCoul()] = true;
            }
            // Trouver la première couleur disponible
            int i=0;
            while (i < this.nbCouleurs && coul_attrib[i] == true) {
                i=i+1;
            }

            if (i < this.nbCouleurs)
                cour.getKey().setCoul(i);
            else
                b=false;
            if (b) {
                //Relâcher la contrainte relative à la coul. des noeuds adj.
                for (SommetAdj sa : lsa) {
                    if (findKeyId(sa.getId()).getCoul()!=-1)
                        coul_attrib[findKeyId(sa.getId()).getCoul()] = false;
                }
            }
        }
        
        return b;
        
    }
    
}