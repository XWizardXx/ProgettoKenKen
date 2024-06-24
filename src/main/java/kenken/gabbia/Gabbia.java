package kenken.gabbia;

import kenken.cella.Cella;

import java.io.Serializable;
import java.util.*;

public interface Gabbia extends Serializable
{
    //aggiunge una cella alla gabbia
    void addCella(Cella cella) throws IllegalArgumentException;

    //verifica che la lista delle celle della gabbia sia piena
    boolean ePieno();

    //verifica che la lista delle celle della gabbia sia vuota
    boolean eVuoto();

    //verifica che le condizioni del blocco siano valide per i valori delle sue celle
    default boolean verifica(int valore)
    {
        List<Cella> celle = celle();
        boolean soloUnaCellaVuota = soloUnaCellaVuota(celle);
        int res = valore;
        switch (segno())
        {
            case SOMMA ->
            {
                for (Cella cella : celle)
                    res += cella.getValore();
                if (soloUnaCellaVuota)
                    return res == valore();
                return res <= valore();
            }
            case MOLTIPLICAZIONE ->
            {
                res = 1;
                for (Cella cella : celle)
                    if (cella.getValore() != 0)
                        res *= cella.getValore();
                if (soloUnaCellaVuota)
                    return res == valore();
                return res <= valore();
            }
            case SOTTRAZI0NE ->
            {
                int primo = celle.get(0).getValore();
                int secondo = celle.get(1).getValore();
                if (primo == 0)
                    if (secondo == 0)
                        return true;
                    else if (Math.max(res, secondo) == res)
                        return res - secondo == valore();
                    else
                        return secondo - res == valore();
                else if (Math.max(res, primo) == res)
                    return res - primo == valore();
                else
                    return primo - res == valore();
            }
            case DIVISIONE ->
            {
                int primo = celle.get(0).getValore();
                int secondo = celle.get(1).getValore();
                if (primo == 0)
                    if (secondo == 0)
                        return true;
                    else if (Math.max(res, secondo) == res)
                        return res / secondo == valore();
                    else
                        return secondo / res == valore();
                else if (Math.max(res, primo) == res)
                    return res / primo == valore();
                else
                    return primo / res == valore();
            }
        }
        return false;
    }

    //restituisce le celle della gabbia
    List<Cella> celle();

    //restituisce il segno della gabbia
    SEGNO segno();

    //restutisce il valore della gabbia
    int valore();

    //verifica che tutti i valori delle celle della gabbia non siano 0 (ovvero non assegnati)
    default boolean soloUnaCellaVuota(List<Cella> celle)
    {
        int cont = 0;
        for (Cella cella : celle)
        {
            if (cella.getValore() == 0)
            {
                cont++;
                if (cont > 1)
                    return false;
            }
        }
        return true;
    }
}
