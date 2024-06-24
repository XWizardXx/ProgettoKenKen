package kenken.gabbia;

import kenken.cella.Cella;

import java.util.ArrayList;
import java.util.List;

public class GabbiaImpl extends GabbiaAbstract
{
    private final List<Cella> celle;

    public GabbiaImpl(int dim)
    {
        super(dim);
        this.celle = new ArrayList<>(dim);
    }

    @Override
    public void addCella(Cella cella) throws IllegalArgumentException
    {
        if (cella == null)
            throw new IllegalArgumentException("cella is null");
        celle.add(cella);
    }

    @Override
    public boolean ePieno()
    {
        return celle.size() >= dim;
    }

    @Override
    public boolean eVuoto()
    {
        return celle.isEmpty();
    }

    @Override
    public List<Cella> celle()
    {
        return celle;
    }

    @Override
    public int hashCode()
    {
        int ret = 0;
        int prime = 11;
        for (Cella cella : celle)
        {
            ret += cella.hashCode();
        }
        ret += segno.hashCode();
        ret += valore * prime;
        return ret;
    }
}
