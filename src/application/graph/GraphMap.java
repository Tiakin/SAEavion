package application.graph;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * La classe GraphMap.
 *
 * @param <T> le type générique
 * @param <E> le type d'élément
 * 
 * @author Killian
 * @author Mohammed Belkhatir
 */
public class GraphMap<T,E> {

    /**
     * La sous-classe SommetPrinc.
     * 
     * @author Killian
     * @author Mohammed Belkhatir
     */
    public class SommetPrinc {

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
         * La temps.
         */
		private LocalTime time;

		/**
         * La durée.
         */
		private Integer duree;

        /**
         * Instancie un nouveau sommet principal.
         *
         * @param id l'id
         * @param valeur la valeur
         * @param lt le temps
         * @param duree la durée
         * 
         * @author Killian
         * @author Mohammed Belkhatir
         */
        public SommetPrinc(int id, T valeur, LocalTime lt, Integer duree) {
            this.id = id;
            this.valeur = valeur;
            this.time = lt;
            this.duree = duree;
        }

        /**
         * Récupère l'id.
         *
         * @return l'id
         * 
         * @author Killian
         * @author Mohammed Belkhatir
         */
        public int getId() {
            return id;
        }

        /**
         * change l'id.
         *
         * @param id le nouveau id
         * 
         * @author Killian
         * @author Mohammed Belkhatir
         */
        public void setId(int id) {
            this.id=id;
        }

        /**
         * Récupère la valeur.
         *
         * @return la valeur
         * 
         * @author Killian
         * @author Mohammed Belkhatir
         */
        public T getVal() {
            return this.valeur;
        }

        /**
         * change la valeur.
         *
         * @param val la nouvelle valeur
         * 
         * @author Mohammed Belkhatir
         */
        public void setVal(T val) {
            this.valeur= val;
        }

        /**
         * Récupère la couleur.
         *
         * @return la couleur
         * 
         * @author Mohammed Belkhatir
         */
        public int getCoul() {
            return this.couleur;
        }

        /**
         * change la couleur.
         *
         * @param c la nouvelle couleur
         * 
         * @author Mohammed Belkhatir
         */
        public void setCoul(int c) {
            this.couleur=c;
        }
        
        /**
         * Récupère la durée.
         *
         * @return la duree
         * 
         * @author Killian
         */
		public Integer getDuree() {
			return duree;
		}
		
		/**
		 * 
         * Récupère le temps.
         *
         * @return le temps
         * 
         * @author Killian
         */
		public LocalTime getTime() {
			return time;
		}

        /**
         * en String.
         *
         * @return le String
         * 
         * @author Killian
         * @author Mohammed Belkhatir
         */
        public String toString() {
            return "Id : "+id+", Valeur : "+valeur.toString()+", " + "Couleur : "+this.couleur+", " + "Temps : "+this.time+", " + "Durée : "+this.duree ;
        }
    }

    /**
     * La sous-classe SommetAdj.
     * 
     * @author Killian
     * @author Mohammed Belkhatir
     */
    public class SommetAdj {

        /**
         * L'id.
         */
        private int id;

        /**
         * La valeur.
         */
        private E valeur;
        
        /**
         * conflit avec SommetPrinc.
         */
        private boolean conflit;

        /**
         * Instancie un nouveau sommet adj.
         *
         * @param id l'id
         * @param valeur la valeur
         * 
         * @author Mohammed Belkhatir
         */
        public SommetAdj(int id, E valeur) {
            this.id = id;
            this.valeur = valeur;
        }

        /**
         * Récupère l'id.
         *
         * @return l'id
         * 
         * @author Mohammed Belkhatir
         */
        public int getId() {
            return this.id;
        }
        
        /**
         * Récupère conflit.
         *
         * @return true si confit avec SommetPrinc
         * 
         * @author Killian
         */
        public boolean isConflit() {
            return this.conflit;
        }
        
        /**
         * Change conflit en true.
         * 
         * @author Killian
         */
        public void causeConflit() {
            this.conflit = true;
        }
        
        /**
         * Récupère la valeur.
         *
         * @return la valeur
         * 
         * @author Mohammed Belkhatir
         */
        public E getValeur() {
            return this.valeur;
        }

        
        /**
         * en string.
         *
         * @return le String
         * 
         * @author Mohammed Belkhatir
         */
        public String toString() {
            return " IdAdj : "+this.id+", en conflit : "+ conflit;
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
    private int kmax;
    
    /** Les couleurs. */
    private String[] colors;

    /**
     * Instancie un nouveau graphmap.
     *
     * @param k le kmax (le nombre de couleur)
     * 
     * @author Killian
     * @author Mohammed Belkhatir
     */
    public GraphMap(int k) {
        this.id=0;
        this.kmax=k;
        map = new HashMap<>();
    }

    /**
     * Trouve le SommetPrinc à partir de sa valeur.
     *
     * @param val la valeur
     * @return le SommetPrinc
     * 
     * @author Mohammed Belkhatir
     */
    @SuppressWarnings("unchecked")
	public SommetPrinc findKeyVal(T val) {
        SommetPrinc sp = null;
        Set<SommetPrinc> s = map.keySet();
        Object[] tabS = s.toArray();
        int i=0;
        while (i<tabS.length && !((SommetPrinc)tabS[i]).getVal().equals(val))
        {i++;}
        if (i<tabS.length) sp=(SommetPrinc)tabS[i];
        return sp;
    }

    /**
     * Trouve le SommetPrinc à partir de son id.
     *
     * @param id l'id
     * @return le SommetPrinc
     * 
     * @author Mohammed Belkhatir
     */
    @SuppressWarnings("unchecked")
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
     * @param lt le temps
     * @param integer la durée
     * @return le SommetPrinc
     * 
     * @author Killian
     * @author Mohammed Belkhatir
     */
    public SommetPrinc addNode(T val, LocalTime lt, Integer integer) {
        SommetPrinc spr = null;
        if (this.findKeyVal(val)==null) {
            spr = new SommetPrinc(this.id++,val, lt, integer);
            map.put(spr, new LinkedList<>());
            System.out.println(val);
        }
        return spr;
    }

    /**
     * Ajoute un edge.
     *
     * @param val1 la valeur 1
     * @param val2 la valeur 2
     * @param valArête la valeur d'arête
     * 
     * @author Killian
     * @author Mohammed Belkhatir
     */
    public void addEdge(T val1, T val2, E valArête) {
        SommetPrinc spr1=this.findKeyVal(val1);
        SommetPrinc spr2=this.findKeyVal(val2);

        if (spr1==null) spr1=addNode(val1, null, null);

        if (spr2==null) spr2=addNode(val2, null, null);

        if (!hasEdge(val1,val2)) {
            map.get(spr1).add(new SommetAdj(spr2.getId(),valArête));
            map.get(spr2).add(new SommetAdj(spr1.getId(),valArête));
        }
        else {
            System.out.println("ces deux vols : "+val1.toString()+ " et "+val2.toString()+" ont déjà été liés");
        }
    }
    
    
    /**
     * Ajoute un edge.
     *
     * @param val1 la valeur 1
     * @param val2 la valeur 2
     * @param valArête la valeur d'arête
     * @param lt1 le temps 1
     * @param integer1 la durée 1
     * @param lt2 le temps 2
     * @param integer2 la durée 2
     * 
     * @author Killian
     */
    public void addEdge(T val1, T val2, E valArête, LocalTime lt1, Integer integer1, LocalTime lt2, Integer integer2) {
        SommetPrinc spr1=this.findKeyVal(val1);
        SommetPrinc spr2=this.findKeyVal(val2);

        if (spr1==null) spr1=addNode(val1, lt1, integer1);

        if (spr2==null) spr2=addNode(val2, lt2, integer2);

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
     * 
     * @author Mohammed Belkhatir
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
     * Récupère les noeud.
     *
     * @return les noeud
     * 
     * @author Killian
     */
    public Set<SommetPrinc> getNodes() {
        return map.keySet();
    }

    /**
     * Récupère le adj.
     *
     * @param sp the sp
     * @return le adj
     * 
     * @author Mohammed Belkhatir
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
     * 
     * @author Mohammed Belkhatir
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
     * 
     * @author Killian
     * @author Mohammed Belkhatir
     */
    public int greedyColoring() {
        int conflits = 0;
        boolean[] coul_attrib = new boolean[this.kmax];

        for (int i=0; i<this.kmax; i++) coul_attrib[i] = false;
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
            while (i < this.kmax && coul_attrib[i]) {
                i++;
            }

            if (i < this.kmax) {
                cour.getKey().setCoul(i);
            } else {
            	int minConflits = Integer.MAX_VALUE;
                int meilleureCouleur = 0;

                for (int c = 0; c < this.kmax; c++) {
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
                //on dit qu'il y a conflit entre les deux noeuds
                for (SommetAdj sa : lsa) {
                    if (findKeyId(sa.getId()).getCoul() == meilleureCouleur) {
                        sa.causeConflit();
                        if(cour.getKey().getId() < sa.getId())
                            conflits++;
                    }
                }
            }

            // Relâcher la contrainte relative à la coul. des noeuds adj.
            for (SommetAdj sa : lsa) {
                if (findKeyId(sa.getId()).getCoul() != -1)
                    coul_attrib[findKeyId(sa.getId()).getCoul()] = false;
            }
        }

        return conflits;
    }
    
    /**
     * Récupère le kmax.
     *
     * @return le kmax
     * 
     * @author Killian
     */
    public int getkmax() {
		return kmax;
    }
    
    /**
     * Initialises les couleurs.
     * 
     * @author Killian
     */
	public void initColors() {
		colors = new String[getkmax()];
        for(int i = 0; i<colors.length;i++) {
        	Random rdm = new Random();
        	colors[i] = "rgb("+rdm.nextInt(256)+","+rdm.nextInt(256)+","+rdm.nextInt(256)+")";
        }
	}
	
	/**
	 * Récupère les couleurs.
	 *
	 * @return les couleurs
	 * 
	 * @author Killian
	 */
	public String[] getColors() {
		return colors;
	}
	
	/**
	 * Récupère une couleur.
	 *
	 * @param i la position
	 * @return la couleur
	 * 
	 * @author Killian
	 */
	public String getColor(int i) {
		return colors[i];
	}
	
}
