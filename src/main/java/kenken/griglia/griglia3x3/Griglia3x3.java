package kenken.griglia.griglia3x3;

import kenken.cella.Cella;
import kenken.gabbia.GabbiaImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public interface Griglia3x3 extends Serializable, Iterable<Cella>, Cloneable
{
    //inserisce il valore val nella cella in posizione x, y
    default void inserisciVal(int x, int y, int val)
    {
        dammiCella(x, y).setValore(val);
    }

    //rimuove il valore nella cella in posizione x, y
    default void rimuoviVal(int x, int y)
    {
        dammiCella(x, y).setValore(0);
    }

    //restituisce la cella in posizione x, y
    Cella dammiCella(int x, int y);

    //verifica che una Cella c sia adiacente alle celle di una gabbia
    default boolean isAdiacente(List<Cella> celle, Cella c)
    {
        for (Cella cella : celle)
            if (celleAdiacenti(cella, c))
                return true;
        return false;
    }

    //verifica che due celle siano adiacenti
    private boolean celleAdiacenti(Cella a, Cella b)
    {
        LinkedList<Cella> ret = adiacenti(a);
        return ret.contains(b);
    }

    /**
     *
     * @return un array list contenente le gabbie della griglia
     */
    ArrayList<GabbiaImpl> gabbie();

    //trova gli adiacenti di una data cella
    LinkedList<Cella> adiacenti(Cella cella);

    //verifica che la posizione di una cella sia valida nella griglia
    default boolean posizioneValida(int i, int j, int dim)
    {
        return i >= 0 && j >= 0 && i <= dim - 1 && j <= dim - 1;
    }

    Iterator<Cella> griglia3x3Iterator();

    //assegna a tutte le celle della griglia3x3 valore 0
    default void svuota()
    {
        Iterator<Cella> it = griglia3x3Iterator();
        while (it.hasNext())
        {
            Cella cella = it.next();
            rimuoviVal(cella.getX(), cella.getY());
        }
    }

    //restituisce la dimensione della griglia3x3
    int dimensione();
}
