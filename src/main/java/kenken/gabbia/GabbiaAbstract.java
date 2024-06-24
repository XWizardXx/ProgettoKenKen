package kenken.gabbia;

import java.util.Random;

public abstract class GabbiaAbstract implements Gabbia
{
    protected int dim;
    protected SEGNO segno;
    protected int valore;

    public GabbiaAbstract(int dim)
    {
        this.dim = dim;
    }

    public int getDim()
    {
        return dim;
    }

    @Override
    public SEGNO segno()
    {
        return segno;
    }

    @Override
    public int valore()
    {
        return valore;
    }

    public void setSegno(SEGNO segno)
    {
        this.segno = segno;
    }

    public void setValore(int valore)
    {
        this.valore = valore;
    }

    @Override
    public String toString()
    {
        String ret = null;
        switch (segno)
        {
            case SOMMA -> ret = "+";
            case DIVISIONE -> ret = "/";
            case MOLTIPLICAZIONE -> ret = "x";
            case SOTTRAZI0NE -> ret = "-";
        }
        return "["+valore+" "+ret+"]";
    }
}
