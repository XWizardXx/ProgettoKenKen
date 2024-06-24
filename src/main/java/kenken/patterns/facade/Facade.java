package kenken.patterns.facade;

import kenken.cella.Cella;
import kenken.gabbia.GabbiaImpl;
import kenken.griglia.griglia3x3.Griglia3x3Impl;

import java.util.ArrayList;
import java.util.Random;

public class Facade
{
    private static final Random random = new Random();

    //crea una singola griglia3x3
    public static Griglia3x3Impl creaGriglia3x3()
    {
        return new Griglia3x3Impl();
    }

    //crea una cella
    public static Cella creaCella(int x, int y)
    {
        return new Cella(x, y);
    }

    //genera le gabbie da usare nella griglia
    public static ArrayList<GabbiaImpl> generaGabbie(int dim)
    {
        ArrayList<GabbiaImpl> ret = new ArrayList<>();
        int nCelle = (int) Math.pow(dim, 2); //numero di celle ancora disponibili
        int r;
        GabbiaImpl g;
        while (nCelle > 0) //fintanto che ho ancora celle disponibili
        {
            r = random.nextInt(2, 5);
            /*
                controllo che:
                se sottraendo la dimensione della gabbia appena creata
                al numero di celle a mia disposizione
                non ottenga un numero negativo, ovvero, ho creato
                una gabbia troppo grande
             */
            if ((nCelle - r) >= 0 && r != 0)
            {
                g = new GabbiaImpl(r);
                ret.add(g);
                nCelle -= g.getDim();
            }
            /*
                controllo che il numero di celle disponibili non sia esattamente 1,
                ciò significherebbe che non c'è più spazio per inserire altre gabbie
                in quanto la dimensione minima è 2 e quindi rimarrebbe una singola cella
                che non potrebbe essere assegnata a nessuna gabbia
             */
            if (nCelle == 1)
            {
                int temp = ret.getLast().getDim();
                ret.removeLast();
                g = new GabbiaImpl(temp + 1);
                ret.add(g);
                nCelle = 0;
            }
        }
        return ret;
    }
}
