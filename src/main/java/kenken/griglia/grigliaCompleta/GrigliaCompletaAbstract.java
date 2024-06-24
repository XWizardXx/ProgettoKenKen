package kenken.griglia.grigliaCompleta;

import kenken.cella.Cella;
import kenken.griglia.DIFFICOLTA;
import kenken.griglia.griglia3x3.Griglia3x3Impl;
import kenken.patterns.templateMethod.Risolutore;

import java.util.LinkedList;

public abstract class GrigliaCompletaAbstract extends Risolutore implements GrigliaCompleta
{
    protected DIFFICOLTA difficolta;
    protected int nSol;
    protected int numG; //numero di griglie3x3
    protected int dim; //dimensione grigliaCompleta

    public GrigliaCompletaAbstract(DIFFICOLTA difficolta, int nSol)
    {
        this.difficolta = difficolta;
        this.nSol = nSol;
        this.numG = assegnaNumeroG(difficolta);
        this.dim = assegnaDimensione(difficolta);
    }

    /**
     *
     * @param difficolta difficoltà scelta per la griglia
     * @return un intero corrispondente alla dimensione della griglia in base alla difficoltà scelta
     */
    private int assegnaDimensione(DIFFICOLTA difficolta)
    {
        int ret = 0;
        switch (difficolta)
        {
            case FACILE -> ret = 3;
            case MEDIO -> ret = 6;
            case DIFFICILE -> ret = 9;
        }
        return ret;
    }

    /**
     *
     * @param difficolta difficoltà scelta per la griglia
     * @return un intero corrispondente al numero di Griglie3x3 che conterrà la GrigliaCompleta in base alla difficoltà scelta
     */
    private int assegnaNumeroG(DIFFICOLTA difficolta)
    {
        int ret = 0;
        switch (difficolta)
        {
            case FACILE -> ret = 1;
            case MEDIO -> ret = 2;
            case DIFFICILE -> ret = 3;
        }
        return ret;
    }

    @Override
    public int dimensione()
    {
        return dim;
    }

    @Override
    public LinkedList<Cella> dammiRiga(int x)
    {
        LinkedList<Cella> ret = new LinkedList<>();
        int[] coord = convertiCoordinate(x, 0);
        for (int j = 0; j < numG; j++)
        {
            Griglia3x3Impl g = dammiGriglia3x3(coord[0], j);
            for (int y = 0; y < g.dimensione(); y++)
            {
                ret.add(g.dammiCella(coord[2], y));
            }
        }
        return ret;
    }

    @Override
    public LinkedList<Cella> dammiColonna(int y)
    {
        LinkedList<Cella> ret = new LinkedList<>();
        int[] coord = convertiCoordinate(0, y);
        for (int i = 0; i < numG; i++)
        {
            Griglia3x3Impl g = dammiGriglia3x3(i, coord[1]);
            for (int x = 0; x < g.dimensione(); x++)
            {
                ret.add(g.dammiCella(x, coord[3]));
            }
        }
        return ret;
    }

    @Override
    public boolean controlla(int x, int y, int valore)
    {

        int cont = 0;
        for (Cella c : dammiRiga(x))
            if (c.getValore() == valore)
            {
                cont++;
                if (cont > 1)
                    return false;
            }
        cont = 0;
        for (Cella c : dammiColonna(y))
            if (c.getValore() == valore)
            {
                cont++;
                if (cont > 1)
                    return false;
            }
        return true;
    }

    @Override
    public boolean controllaGabbie(int x, int y, int valore)
    {
        return dammiCella(x, y).getG().verifica(valore);
    }

    //------------------------------------------------// metodi risolutore
    @Override
    protected boolean assegnabile(int x, int y, int valore, boolean controllaGabbia)
    {
        for (Cella c : dammiRiga(x))
            if (c.getValore() == valore)
                return false;
        for (Cella c : dammiColonna(y))
            if (c.getValore() == valore)
                return false;
        if (controllaGabbia)
            if (!dammiCella(x, y).getG().verifica(valore))
                return false;
        return true;
    }

    @Override
    protected void assegna(int x, int y, int valore)
    {
        inserisciValore(x, y, valore);
    }

    @Override
    protected void deassegna(int x, int y)
    {
        rimuoviValore(x, y);
    }

    @Override
    protected int dimensioneR()
    {
        return dimensione();
    }
    //------------------------------------------------//
}
