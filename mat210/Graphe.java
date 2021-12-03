package mat210;

/**
 * Fichier distribué dans le cadre du cours MAT210, session automne 2021, à l'ÉTS.
 *
 * @author Xavier Provençal
 *
 * Modifications par les étudiant.e.s : 
 *  - Esteban Sanchez
 *  - Tristan Fecteau
 *  - Anyin Zhang
 */
 
import java.util.Iterator;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collections; // Pour inverser un ArrayList avec Collections.reverse()


/**
 * Classe abstraite qui représente un graphe orienté et pondéré.
 *
 * Par convention, les sommets d'un graphe à `n` sommets sont identifiez par
 * les entiers de 0 à n-1.
 */
public abstract class Graphe {

    //
    // Classe interne : Arc (implémentée à la fin de ce fichier)
    //

    // 
    // Variables d'instance
    //
    protected int nbSommets;
    protected double ponderationArcsAbsents;


    //
    // Méthodes
    //

    /**
     * Retourne le nombre de sommets du graphe.
     *
     * @return ne nombre de sommets du graphe
     */
    public int getNbSommets() {
        return nbSommets;
    }

    /**
     * Retourne la pondération choisie pour les arcs absents.
     *
     * @return la pondération des arcs absents
     */
    public double getPonderationArcsAbsents() {
        return ponderationArcsAbsents;
    }


    /**
     * Ajoute un arc au graphe.
     *
     * @param initial sommet initial de l'arc
     * @param terminal sommet terminal de l'arc
     * @param ponderation poids de l'arc.
     */
    public abstract void ajouterArc(int initial, int terminal, double ponderation);


    /**
     * Retourne un itateur sur les arcs dont le sommet spécifié est le sommet
     * initial.
     *
     * @param sommet le sommet initial.
     * @return un itérateur sur les arcs partant du sommet.
     */
    public abstract Iterator<Arc> getArcs(int sommet);


    /**
     * Retourne la pondération d'un arc s'il existe, sinon la valeur
     * `ponderationArcsAbsents` est retournée.
     *
     * @param initial sommet initial de l'arc
     * @param terminal sommet terminal de l'arc
     * @return la pondération
     */
    public abstract double getPonderation(int initial, int terminal);



    /**
     * Pour affichage sous la forme de texte. À utiliser que pour de petits
     * graphes.
     *
     * @return description textuelle du graphe
     */
    public abstract String toString();


    /**
     * Type énumératif pour le choix de le représentation. Deux choix sont
     * supportés : par listes d'adjacence ou par matrice d'adjacence.
     */
    public static enum Representation {
        Listes("Listes"), 
        Matrice("Matrice");

        /**
         * Constructeur à partir du nom
         *
         * @param nom le nom de la représentation
         */ 
        Representation(String nom) {
            this.nom = nom;
        }
        String nom;
    }


    /////////////////////////////////////////////////////
    //
    // Algorithmes de recherche de chemin minimaux
    //


    /**
     * Construit un chemin à partir du tableau `predecesseur` calculé par
     * l'algorithme de Dijkstra.
     *
     * Étant donné un tel tableau et le sommet `destination`, cette fonction
     * construit un chemin sous la forme d'un ArrayList d'entiers.
     * 
     * La case 0 de cet ArrayList est le point de départ du chemin.
     * La dernière case est le sommet `destination`.
     *
     * @param predecesseur tableau calculé par l'algorithme de Dijkstra
     * @param destination le sommet où le chemin termine
     * @return un tableau décrivant un chemin dans le graphe
     */
    public ArrayList<Integer> tableauPredecesseurVersChemin(
            ArrayList<Integer> predecesseur,
            int destination) {
        ArrayList<Integer> chemin =new ArrayList<Integer>();
        int i=destination;
        while (i!=-1){
            chemin.add(i);
            i=predecesseur.get(i);
        }
         Collections.reverse(chemin);
        return chemin;
    }

    /**
     * Retourne le chemin minimal
     *
     * @param  L Liste de la pondérations des chemins à partir du point de départ
     * @param  S tableau booléen des Sommets dont on a trouvé le minimum
     * @return un chemin minimal de `depart` à `destination`
     */
    public int TrouverSommetMinimum(ArrayList<Double> L, boolean[] S)
    {
        int i=0;
        double plusPetitTrouver=Double.POSITIVE_INFINITY;
        int indexMinimum=-1;
        while (i<nbSommets) {
            if(!S[i]){
                double actuel=L.get(i);
                if(plusPetitTrouver>=actuel){
                    plusPetitTrouver=actuel;
                    indexMinimum=i;
                }
            }
            i++;
        }
        return indexMinimum;
    }

    /**
     * Retourne le chemin minimal
     *
     * @param  L Liste de la pondérations des chemins à partir du point de départ
     * @param  S tableau booléen des Sommets dont on a trouvé le minimum
     * @return un chemin minimal de `depart` à `destination`
     */
    public int TrouverSommetMinimum(ArrayList<Double> L, ArrayList<Integer> S)
    {
        int i=0;
        double plusPetitTrouver=Double.POSITIVE_INFINITY;
        int indexMinimum=-1;
        while (i<nbSommets) {
            if(!S.contains(i)){
                double actuel=L.get(i);
                if(plusPetitTrouver>=actuel){
                    plusPetitTrouver=actuel;
                    indexMinimum=i;
                }
            }
            i++;
        }
        return indexMinimum;
    }
    /**
     * Retourne le chemin minimal
     *
     * @param  L Liste de la pondérations des chemins à partir du point de départ
     * @param  S tableau booléen des Sommets dont on a trouvé le minimum
     * @return un chemin minimal de `depart` à `destination`
     */
    public int TrouverSommetMinimum(ArrayList<Double> L, TreeSet<Integer> S)
    {
        int i=0;
        double plusPetitTrouver=Double.POSITIVE_INFINITY;
        int indexMinimum=-1;
        while (i<nbSommets) {
            if(!S.contains(i)){
                double actuel=L.get(i);
                if(plusPetitTrouver>=actuel){
                    plusPetitTrouver=actuel;
                    indexMinimum=i;
                }
            }
            i++;
        }
        return indexMinimum;
    }


    /**
     * Utilise l'algorithme de Dijkstra pour calculer un chemin de coût minimum
     * du sommet `depart` au sommet `destination`.
     *
     * Le chemin est retourné sous la forme d'un ArrayList<Integer> dont la
     * case 0 contient `depart` et la dernière case contient `destination`.
     *
     * @param  depart le sommet où commence le chemin
     * @param  destination le sommet où termine le chemin
     * @return un chemin minimal de `depart` à `destination`
     */
    public ArrayList<Integer> DijkstraParArrayList(int depart, int destination)
    {
        ArrayList<Integer> S=new ArrayList<Integer>(nbSommets);
        ArrayList<Integer> P=new ArrayList<Integer>(nbSommets);
        ArrayList<Double> L=new ArrayList<Double>(nbSommets);
        for (int i=0;i<nbSommets;i++){
            P.add(i);
            L.add(Double.POSITIVE_INFINITY);
        }
        int n=depart;
        P.set(n,-1);
        L.set(n,0.0);
        while (n!=destination){
            Iterator<Arc> arcIterator=getArcs(n);
            while (arcIterator.hasNext()){
                Arc arc=arcIterator.next();
                Double ponderation_plus=arc.ponderation+L.get(n);
                if(ponderation_plus<L.get(arc.terminal)){
                    P.set(arc.terminal,n);
                    L.set(arc.terminal,ponderation_plus);
                }
            }
            S.add(n);
            n= TrouverSommetMinimum(L,S);
        }

        return tableauPredecesseurVersChemin(P,destination);
    }


    /**
     * Utilise l'algorithme de Dijkstra pour calculer un chemin de coût minimum
     * du sommet `depart` au sommet `destination`.
     *
     * Le chemin est retourné sous la forme d'un ArrayList<Integer> dont la
     * case 0 contient `depart` et la dernière case contient `destination`.
     *
     * @param  depart le sommet où commence le chemin
     * @param  destination le sommet où termine le chemin
     * @return un chemin minimal de `depart` à `destination`
     */
    public ArrayList<Integer> DijkstraParTreeSet(int depart, int destination) {
        TreeSet<Integer> S=new TreeSet<Integer>();
        ArrayList<Integer> P=new ArrayList<Integer>(nbSommets);
        ArrayList<Double> L=new ArrayList<Double>(nbSommets);
        for (int i=0;i<nbSommets;i++){
            P.add(i);
            L.add(Double.POSITIVE_INFINITY);
        }
        int n=depart;
        P.set(n,-1);
        L.set(n,0.0);
        while (n!=destination){
            Iterator<Arc> arcIterator=getArcs(n);
            while (arcIterator.hasNext()){
                Arc arc=arcIterator.next();
                Double ponderation_plus=arc.ponderation+L.get(n);
                if(ponderation_plus<L.get(arc.terminal)){
                    P.set(arc.terminal,n);
                    L.set(arc.terminal,ponderation_plus);
                }
            }
            S.add(n);
            n= TrouverSommetMinimum(L,S);
        }

        return tableauPredecesseurVersChemin(P,destination);
    }


    /**
     * Utilise l'algorithme de Dijkstra pour calculer un chemin de coût minimum
     * du sommet `depart` au sommet `destination`.
     *
     * Le chemin est retourné sous la forme d'un ArrayList<Integer> dont la
     * case 0 contient `depart` et la dernière case contient `destination`.
     *
     * @param  depart le sommet où commence le chemin
     * @param  destination le sommet où termine le chemin
     * @return un chemin minimal de `depart` à `destination`
     */
    public ArrayList<Integer> DijkstraParBooleanArray(int depart, int destination) {
        boolean[] S=new boolean[nbSommets];
        ArrayList<Integer> P=new ArrayList<Integer>(nbSommets);
        ArrayList<Double> L=new ArrayList<Double>(nbSommets);
        for (int i=0;i<nbSommets;i++){
            S[i]=false;
            P.add(i);
            L.add(Double.POSITIVE_INFINITY);
        }
        int n=depart;
        P.set(n,-1);
        L.set(n,0.0);
        while (n!=destination){
            Iterator<Arc> arcIterator=getArcs(n);
            while (arcIterator.hasNext()){
                Arc arc=arcIterator.next();
                Double ponderation_plus=arc.ponderation+L.get(n);
                if(ponderation_plus<L.get(arc.terminal)){
                    P.set(arc.terminal,n);
                    L.set(arc.terminal,ponderation_plus);
                }
            }
            S[n]=true;
            n=TrouverSommetMinimum(L,S);
        }

        return tableauPredecesseurVersChemin(P,destination);
    }

    /**
     * Utilise l'algorithme de Astar pour calculer un chemin de coût minimum
     * du sommet `depart` au sommet `destination`.
     *
     * Le chemin est retourné sous la forme d'un ArrayList<Integer> dont la
     * case 0 contient `depart` et la dernière case contient `destination`.
     *
     * @param  depart le sommet où commence le chemin
     * @param  destination le sommet où termine le chemin
     * @return un chemin minimal de `depart` à `destination`
     */
    public ArrayList<Integer> Astar(int depart, int destination, Carte.HeuristiquePourAStar h) {
        // 
        // BONUS
        //
        //return tableauPredecesseurVersChemin(predecesseur, destination);
        return null;
    }

    /**
     * Un arc est une arête orientée
     *
     * Un arc est un triplet d'informations :
     *  - initial : (int) le sommet initial de l'arc.
     *  - terminal : (int) le sommet terminal de l'arc.
     *  - ponderation : (double) le poids de l'arc.
     *
     * Toutes ces données sont publiques et peuvent être accédées directement.
     */
    public class Arc {

        //
        // Variables d'instance
        //
        public int initial;
        public int terminal;
        public double ponderation;


        // 
        // Constructeur
        //

        /**
         * Constructeur pour un arc allant du sommet `a` au sommet `b` avec
         * pondération `p`.
         *
         * @param  a sommet initial de l'arc
         * @param  b sommet terminal de l'arc
         * @param  p pondération de l'arc
         */
        public Arc(int a, int b, double p) {
            initial = a;
            terminal = b;
            ponderation = p;
        }

        //
        // Méthodes
        //

        /**
         * Test l'égalité entre deux arcs
         * 
         * @param  objet l'object avec lequel on teste l'égalité
         * @return true si les deux sont égaux, faux sinon
         */
        @Override
        public boolean equals(Object objet) {
            if (objet == null){
                return false;
            }
            if (! objet.getClass().equals(getClass())) {
                return false;
            }
            final Arc other = (Arc) objet;
            return initial == other.initial 
                && terminal == other.terminal 
                && ponderation == other.ponderation;
        }

        /**
         * Fonction de hachage nécessaire pour l'insertion d'un objet dans
         * certaines structures de données.
         *
         * @return une valeur de hachage pour l'arc.
         */
        @Override
        public int hashCode() {
            return ((initial << 16) + terminal) ^ Double.valueOf(ponderation).hashCode();
        }

    }

}


