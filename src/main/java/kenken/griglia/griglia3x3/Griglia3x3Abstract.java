package kenken.griglia.griglia3x3;

import kenken.cella.Cella;
import kenken.griglia.DIFFICOLTA;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class Griglia3x3Abstract implements Griglia3x3
{
    protected DIFFICOLTA difficolta;
    protected final int dim; //dimensione gabbia3x3
    protected int nSol;

    public Griglia3x3Abstract()
    {
        this.dim = 3;
    }

    public LinkedList<Cella> adiacenti(Cella cella)
    {
        LinkedList<Cella> ret = new LinkedList<>();
        int i = cella.getX();
        int j = cella.getY();
        //controllo che le posizioni adiacenti a quelle della cella data siano valide, se si le aggiungo alla lista degli adiacenti
        if (posizioneValida(i, j + 1, dim))
            ret.add(dammiCella(i, j + 1));
        if (posizioneValida(i, j - 1, dim))
            ret.add(dammiCella(i, j - 1));
        if (posizioneValida(i - 1, j, dim))
            ret.add(dammiCella(i - 1, j));
        if (posizioneValida(i + 1, j, dim))
            ret.add(dammiCella(i + 1, j));
        return ret;
    }

    @Override
    public int dimensione()
    {
        return dim;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Griglia3x3Abstract that)) return false;
        return dim == that.dim && nSol == that.nSol && difficolta == that.difficolta;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(difficolta, dim, nSol);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++)
        {
            sb.append("[");
            for (int j = 0; j < dim; j++)
            {
                Cella corrente = dammiCella(i, j);
                sb.append(corrente.toString());
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    //--------------------// iteratore
    public Iterator<Cella> iterator()
    {
        return new Griglia3x3Iterator();
    }

    private class Griglia3x3Iterator implements Iterator<Cella>
    {
        private int i = -1;
        private int j = -1;

        @Override
        public boolean hasNext()
        {
            return i + j <  (dim * 2) - 2;
        }

        @Override
        public Cella next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            j++;
            if (j == 3 || i < 0)
            {
                i ++;
                j = 0;
            }
            return dammiCella(i, j);
        }
    }
}
