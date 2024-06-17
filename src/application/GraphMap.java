package application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.GraphMap.SommetAdj;
import application.GraphMap.SommetPrinc;

/**
 * La classe GraphMap.
 *
 * @param <T> le type générique
 * @param <E> le type d'élément
 */
class GraphMap<T,E> {

    /**
     * La sous-classe SommetPrinc.
     */
    class SommetPrinc {

        /**
         * L'id.
         */
        private int id;

        /**
         * La valeur.
         */
        private T valeur;

        /**
         * La couleur.
         */
        private int couleur=-1;

        /**
         * Instancie un nouveau sommet principal.
         *
         * @param id l'id
         * @param valeur la valeur
         */
        public SommetPrinc(int id, T valeur) {
            this.id = id;
            this.valeur = valeur;
        }

        /**
         * Récupère l'id.
         *
         * @return l'id
         */
        public int getId() {
            return id;
        }

        /**
         * change l'id.
         *
         * @param id le nouveau id
         */
        public void setId(int id) {
            this.id=id;
        }

        /**
         * Récupère la valeur.
         *
         * @return la valeur
         */
        public T getVal() {
            return this.valeur;
        }

        /**
         * change la valeur.
         *
         * @param val la nouvelle valeur
         */
        public void setVal(T val) {
            this.valeur= val;
        }

        /**
         * Récupère la couleur.
         *
         * @return la couleur
         */
        public int getCoul() {
            return this.couleur;
        }

        /**
         * change la couleur.
         *
         * @param c la nouvelle couleur
         */
        public void setCoul(int c) {
            this.couleur=c;
        }


        /**
         * en String.
         *
         * @return le String
         */
        public String toString() {
            return "Id : "+id+", Valeur : "+valeur.toString()+", " + "Couleur : "+this.couleur ;
        }
    }

    /**
     * La sous-classe SommetAdj.
     */
    class SommetAdj {

        /**
         * L'id.
         */
        private int id;

        /**
         * La valeur.
         */
        private E valeur;

        /**
         * Instancie un nouveau sommet adj.
         *
         * @param id l'id
         * @param valeur la valeur
         */
        public SommetAdj(int id, E valeur) {
            this.id = id;
            this.valeur = valeur;
        }

        /**
         * Récupère l'id.
         *
         * @return l'id
         */
        public int getId() {
            return this.id;
        }

        /**
         * Récupère la valeur.
         *
         * @return la valeur
         */
        public E getValeur() {
            return this.valeur;
        }

        /**
         * en string.
         *
         * @return le String
         */
        public String toString() {
            return " IdAdj : "+this.id+", ValeurArête : "+ valeur.toString();
        }
    }

    /**
     * La map.
     */
    private Map<SommetPrinc, List<SommetAdj>> map;

    /**
     * L'id.
     */
    private int id;

    /**
     * Le nombre de couleurs max.
     */
    private int nbCouleurs;

    /**
     * Instancie un nouveau graphmap.
     *
     * @param k le kmax (le nombre de couleur)
     */
    public GraphMap(int k) {
        this.id=0;
        this.nbCouleurs=k;
        map = new HashMap<>();
    }

    /**
     * Trouve le SommetPrinc à partir de sa valeur.
     *
     * @param val la valeur
     * @return le SommetPrinc
     */
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

    /**
     * Trouve le SommetPrinc à partir de son id.
     *
     * @param id l'id
     * @return le SommetPrinc
     */
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

    /**
     * Ajoute une noeud.
     *
     * @param val la valeur
     * @return le SommetPrinc
     */
    public SommetPrinc addNode(T val) {
        SommetPrinc spr = null;
        if (this.findKeyVal(val)==null) {
            spr = new SommetPrinc(this.id++,val);
            map.put(spr, new LinkedList<>());
        }
        return spr;
    }

    /**
     * Ajoute un edge.
     *
     * @param val1 la valeur 1
     * @param val2 la valeur 2
     * @param valArête la valeur d'arête
     */
    public void addEdge(T val1, T val2, E valArête) {
        SommetPrinc spr1=this.findKeyVal(val1);
        SommetPrinc spr2=this.findKeyVal(val2);

        if (spr1==null) spr1=addNode(val1);

        if (spr2==null) spr2=addNode(val2);

        if (!hasEdge(val1,val2)) {
            map.get(spr1).add(new SommetAdj(spr2.getId(),valArête));
            map.get(spr2).add(new SommetAdj(spr1.getId(),valArête));
        }
        else {
            System.out.println("ces deux vols : "+val1.toString()+ " et "+val2.toString()+" ont déjà été liés");
        }
    }

    /**
     * Vérifie l'existance de edge.
     *
     * @param id1 l'id 1
     * @param id2 l'id 2
     * @return true, si c'est vrai
     */
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

    /**
     * Récupère le noeud.
     *
     * @return le noeud
     */
    public Set<SommetPrinc> getNodes() {
        return map.keySet();
    }

    /**
     * Récupère le adj.
     *
     * @param sp the sp
     * @return le adj
     */
    public List<SommetAdj> getAdj(SommetPrinc sp) {
        return map.get(sp);
    }

    /**
     * Vérifie l'existance de edge.
     *
     * @param val1 the val 1
     * @param val2 the val 2
     * @return true, si c'est vrai
     */
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

    /**
     * To string.
     *
     * @return le String
     */
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

    /**
     * Greedy coloring.
     *
     * @return le nombre de conflits
     */
    public int greedyColoring() {
        int conflits = 0;
        boolean[] coul_attrib = new boolean[this.nbCouleurs];

        for (int i=0; i<this.nbCouleurs; i++) coul_attrib[i] = false;
        Iterator<Map.Entry<SommetPrinc, List<SommetAdj>>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<SommetPrinc, List<SommetAdj>> cour = iterator.next();
            // Indiquer que les coul. des noeuds adjacents sont attribuées
            List<SommetAdj> lsa = cour.getValue();

            for (SommetAdj sa : lsa) {
                if (findKeyId(sa.getId()).getCoul() != -1)
                    coul_attrib[findKeyId(sa.getId()).getCoul()] = true;
            }
            // Trouver la première couleur disponible
            int i=0;
            while (i < this.nbCouleurs && coul_attrib[i]) {
                i++;
            }

            if (i < this.nbCouleurs) {
                cour.getKey().setCoul(i);
            } else {
            	int minConflits = Integer.MAX_VALUE;
                int meilleureCouleur = 0;

                for (int c = 0; c < this.nbCouleurs; c++) {
                    int countConflits = 0;
                    for (SommetAdj sa : lsa) {
                        if (findKeyId(sa.getId()).getCoul() == c) {
                            countConflits++;
                        }
                    }
                    if (countConflits < minConflits) {
                        minConflits = countConflits;
                        meilleureCouleur = c;
                    }
                }

                cour.getKey().setCoul(meilleureCouleur);
                conflits += minConflits;
            }

            // Relâcher la contrainte relative à la coul. des noeuds adj.
            for (SommetAdj sa : lsa) {
                if (findKeyId(sa.getId()).getCoul() != -1)
                    coul_attrib[findKeyId(sa.getId()).getCoul()] = false;
            }
        }

        return conflits;
    }
    
}
