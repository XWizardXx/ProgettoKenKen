package kenken.griglia.griglia3x3;

import kenken.cella.Cella;
import kenken.gabbia.GabbiaImpl;
import kenken.gabbia.SEGNO;
import kenken.patterns.facade.Facade;

import java.util.*;

public class Griglia3x3Impl extends Griglia3x3Abstract
{
    private final ArrayList<GabbiaImpl> gabbie;
    private final Cella[][] griglia;

    public Griglia3x3Impl()
    {
        super();
        griglia = new Cella[dim][dim];
        for (int x = 0; x < dim; x++)
            for (int y = 0; y < dim; y++)
                griglia[x][y] = Facade.creaCella(x, y);
        this.gabbie = Facade.generaGabbie(this.dim);
    }

    /**
     * questo cotruttore è usato esclusivamente per creare un clone
     * @param griglia contiene le celle della griglia
     */
    private Griglia3x3Impl(Cella[][] griglia)
    {
        this.griglia = griglia;
        this.gabbie = null;
    }

    //assegna le celle alle gabbie
    public void popola()
    {
        Iterator<GabbiaImpl> gIt = gabbie.iterator();
        if (gIt.hasNext())
        {
            GabbiaImpl gabbia = gIt.next();
            Iterator<Cella> cIt = griglia3x3Iterator();
            while (cIt.hasNext())
            {
                Cella cella = cIt.next();
                if (gabbia.ePieno())
                {
                    assegnaValoreESegno(gabbia);
                    if (gIt.hasNext())
                        gabbia = gIt.next();
                    else
                        return;
                }
                if (gabbia.eVuoto() && !cella.appartiene())
                {
                    cella.setG(gabbia);
                    gabbia.addCella(cella);
                }
                LinkedList<Cella> adiacenti;
                if (gabbia.eVuoto())
                    adiacenti = adiacenti(cella);
                else
                    adiacenti = adiacenti(gabbia.celle().getLast());
                for (Cella c : adiacenti)
                {
                    if (gabbia.eVuoto() && !c.appartiene())
                    {
                        c.setG(gabbia);
                        gabbia.addCella(c);
                    }
                    else if (!gabbia.ePieno() && !c.appartiene() && isAdiacente(gabbia.celle(), c))
                    {
                        c.setG(gabbia);
                        gabbia.addCella(c);
                    }
                }
            }
        }
    }

    /**
     *
     * @param gabbia gabbia alla quale viene assegnato segno e valore
     */
    private void assegnaValoreESegno(GabbiaImpl gabbia)
    {
        List<Cella> celle = gabbia.celle();
        Collections.sort(celle);
        Collections.reverse(celle);
        /*
            questo controllo viene fatto perchè le gabbie
            con segno divisione e sottrazzione possono avere solo dimensione 2
         */
        if (gabbia.getDim() == 2)
        {
            if (celle.get(0).getValore() / celle.get(1).getValore() % 2 == 0)
            {
                gabbia.setSegno(SEGNO.DIVISIONE);
                gabbia.setValore(celle.get(0).getValore() / celle.get(1).getValore());
            }
            else
            {
                gabbia.setSegno(SEGNO.SOTTRAZI0NE);
                gabbia.setValore(celle.get(0).getValore() - celle.get(1).getValore());
            }
        }
        /*
            tutte le altre invece avranno segno moltiplicazione o somma
         */
        else
        {
            if (gabbia.getDim() % 2 == 0)
            {
                gabbia.setSegno(SEGNO.MOLTIPLICAZIONE);
                int mul = 1;
                for (Cella cella : celle)
                    mul *= cella.getValore();
                gabbia.setValore(mul);
            }
            else
            {
                gabbia.setSegno(SEGNO.SOMMA);
                int sum = 0;
                for (Cella cella : celle)
                    sum += cella.getValore();
                gabbia.setValore(sum);
            }
        }
    }

    @Override
    public ArrayList<GabbiaImpl> gabbie()
    {
        return gabbie;
    }

    @Override
    public Cella dammiCella(int x, int y)
    {
        return griglia[x][y];
    }

    @Override
    public Iterator<Cella> griglia3x3Iterator()
    {
        return iterator();
    }

    @Override
    public Griglia3x3Impl clone()
    {
        Cella[][] celle = new Cella[dim][dim];
        for (int i = 0; i < celle.length; i++)
            for (int j = 0; j < celle.length; j++)
                celle[i][j] = dammiCella(i, j).clone();
        return new Griglia3x3Impl(celle);
    }
}
