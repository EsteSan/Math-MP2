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

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.StringBuilder;
import java.text.DecimalFormat;

/**
 * Implémentation de graphes représentés par des listes d'adjacences.
 */
public class GrapheParListes extends Graphe {


    /**
     * Constructeur
     *
     * Initialise un graphe dont le nombre de sommet est `nbSommets` qui ne
     * contient aucun arc.
     * 
     * @param nbSommets le nombre de sommet du grpahe
     * @param ponderationArcsAbsents la ponderation affectée aux arcs qui ne
     *        font pas partie du graphe
     */
    public GrapheParListes(int nbSommets, double ponderationArcsAbsents) {
        this.nbSommets=nbSommets;
        this.ponderationArcsAbsents=ponderationArcsAbsents;
        listes=new ArrayList<ArrayList<Arc>>();
        for(int i=0;i<nbSommets;i++){
            ArrayList<Arc> arcs=new ArrayList<Arc>();
            listes.add(i,arcs);
        }
    }


    /**
     * {@inheritdoc}
     */
    @Override
    public void ajouterArc(int initial, int terminal, double ponderation) {
        listes.get(initial).add(new Arc(initial,terminal,ponderation));
    }


    /**
     * {@inheritdoc}
     */
    @Override
    public Iterator<Arc> getArcs(int sommet) {
        return listes.get(sommet).iterator();
    }


    /**
     * Retourne l'objet arc correspondant, s'il existe, ``null`` sinon.
     *
     * @param  initial le sommet initial de l'arc
     * @param  terminal le sommet terminal de l'arc
     * @return l'arc allant du sommet `initial` vers le sommet `terminal` s'il
     *         existe, null sinon.
     */
    private Arc trouverArc(int initial, int terminal) {
        ArrayList<Arc> listArc=listes.get(initial);
        int i = 0;
        while (i<listArc.size()){
            Arc arc=listArc.get(i++);
            if(arc.initial==initial&&arc.terminal==terminal){
                return arc;
            }
        }
        return null;
    }


    /**
     * {@inheritdoc}
     */
    @Override
    public double getPonderation(int initial, int terminal) {
        Arc arc=trouverArc(initial,terminal);
        return arc!=null?arc.ponderation:this.ponderationArcsAbsents;
    }

    /**
     * Produit une représentation textuelle du graphe où toutes les listes
     * d'adjacences sont listées. 
     *
     * À n'utiliser que pour de petits graphes.
     *
     * @return représentation textuelle du graphe.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0.0");
        int nbDigits = (int) Math.ceil(Math.log10((double)nbSommets));
        for (int i=0; i<listes.size(); ++i) {
            ArrayList<Arc> l = listes.get(i);
            sb.append(String.format("%"+nbDigits+"d", i));
            sb.append(" -> [");
            if (!l.isEmpty()) {
                Iterator<Arc> it = l.iterator();
                Arc a = it.next();
                sb.append("(" + a.initial + "," + a.terminal + "," + a.ponderation + ")");
                while (it.hasNext()) {
                    a = it.next();
                    sb.append(", (" + a.initial + "," + a.terminal + "," + df.format(a.ponderation) + ")");
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }



    // Tableau contenant toutes les listes d'adjacences du graphe.
    //
    // ``listes.get(i)`` est un tableau contenant les arêtes dont le sommet initial est ``i``.
    //
    ArrayList<ArrayList<Arc>> listes;

}
