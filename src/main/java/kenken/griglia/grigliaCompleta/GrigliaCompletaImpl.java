package kenken.griglia.grigliaCompleta;

import kenken.cella.Cella;
import kenken.griglia.DIFFICOLTA;
import kenken.griglia.griglia3x3.Griglia3x3Impl;
import kenken.patterns.facade.Facade;
import kenken.patterns.memento.Memento;
import kenken.patterns.memento.Originator;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class GrigliaCompletaImpl extends GrigliaCompletaAbstract implements Originator
{
    private final LinkedList<Memento> listaSoluzioni = new LinkedList<>();
    public final Griglia3x3Impl[][] grigliaCompleta;

    public GrigliaCompletaImpl(DIFFICOLTA difficolta, int nSol)
    {
        super(difficolta, nSol);
        this.grigliaCompleta = new Griglia3x3Impl[numG][numG];
        for (int i = 0; i < numG; i++)
            for (int j = 0; j < numG; j++)
                grigliaCompleta[i][j] = Facade.creaGriglia3x3();
        assegnaValoriTemp();
        popola();
        int cont = 0;
        while (cont <= nSol)
        {
            svuota();
            risolvi(true);
            listaSoluzioni.add(salva());
            cont++;
        }
        svuota();
    }

    /**
     * rimuove tutti i valori dalle celle della griglia
     */
    private void svuota()
    {
        for (int i = 0; i < numG; i++)
            for (int j = 0; j < numG; j++)
                grigliaCompleta[i][j].svuota();
    }

    /**
     * popola le singole Griglie3x3 che compomgono la GrigliaCompleta
     */
    private void popola()
    {
        for (int i = 0; i < numG; i++)
            for (int j = 0; j < numG; j++)
                grigliaCompleta[i][j].popola();
    }

    /**
     * assegna valori temporanei alla griglia per poter stabilire i segni ed i valori delle gabbie nella griglia
     */
    private void assegnaValoriTemp()
    {
        risolvi(false);
    }

    @Override
    public LinkedList<Memento> listaSoluzioni()
    {
        return listaSoluzioni;
    }

    @Override
    public Cella dammiCella(int x, int y)
    {
        int[] coord = convertiCoordinate(x, y);
        return grigliaCompleta[coord[0]][coord[1]].dammiCella(coord[2], coord[3]);
    }

    @Override
    public Griglia3x3Impl dammiGriglia3x3(int i, int j) throws NoSuchElementException
    {
        return grigliaCompleta[i][j];
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numG; i++)
        {
            for (int j = 0; j < numG; j++)
                sb.append(grigliaCompleta[i][j].toString()).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }

    //------------------------------// metodi originator
    @Override
    public Memento salva()
    {
        return new MememntoGriglia();
    }

    @Override
    public void ripristina(Memento m)
    {
        if (!(m instanceof MememntoGriglia mememntoGriglia))
            throw new IllegalArgumentException("Memento non corretto");
        for (int i = 0; i < grigliaCompleta.length; i++)
            for (int j = 0; j < grigliaCompleta.length; j++)
                grigliaCompleta[i][j] = mememntoGriglia.grigliaCompleta[i][j].clone();
    }

    //------------------------------// memento
    private class MememntoGriglia implements Memento, Serializable
    {
        private final Griglia3x3Impl[][] grigliaCompleta = new Griglia3x3Impl[numG][numG];

        private MememntoGriglia()
        {
            for (int i = 0; i < grigliaCompleta.length; i++)
                for (int j = 0; j < grigliaCompleta.length; j++)
                    grigliaCompleta[i][j] = dammiGriglia3x3(i, j).clone();
        }
    }
}