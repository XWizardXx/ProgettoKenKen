package kenken.cella;

import kenken.gabbia.GabbiaImpl;

import java.io.Serializable;

public class Cella implements Comparable<Cella>, Serializable, Cloneable
{
    private GabbiaImpl g = null;
    private int valore = 0;
    private int x;
    private int y;

    public Cella(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setG(GabbiaImpl g)
    {
        this.g = g;
    }

    //usato solo in fase di test
    public GabbiaImpl getG()
    {
        return g;
    }

    public boolean appartiene()
    {
        return g != null;
    }

    public void setValore(int valore)
    {
        this.valore = valore;
    }

    public int getValore()
    {
        return valore;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public int compareTo(Cella c)
    {
        return Integer.compare(this.valore, c.valore);
    }

    @Override
    public String toString()
    {
        if (valore == 0)
            return "( ) " + g.toString();
        return "(" + valore + ") " + g.toString();
    }

    @Override
    public int hashCode()
    {
        int prime = 11;
        return valore * prime + x * prime + y * prime;
    }

    @Override
    public Cella clone()
    {
        Cella cella = new Cella(this.getX(), this.getY());
        cella.setValore(this.getValore());
        return cella;
    }
}
