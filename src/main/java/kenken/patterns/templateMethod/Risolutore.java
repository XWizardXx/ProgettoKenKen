package kenken.patterns.templateMethod;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Risolutore
{
    /**
     *
     * @return i valori che possono essere inseriti alle celle della griglia
     */
    protected final ArrayList<Integer> scelte()
    {
        ArrayList<Integer> scelte = new ArrayList<>();
        for (int i = 1; i <= dimensioneR(); i++)
        {
            scelte.add(i);
        }
        return scelte;
    }

    /**
     *
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @param valore valore da inserire nella cella x, y
     * @param controllaGabbie indica se le gabbie debbano essere controllate o meno
     * @return true se il valore può essere assegnato alla cella x, y
     */
    protected abstract boolean assegnabile(int x, int y, int valore, boolean controllaGabbie);

    /**
     * assegna il valore alla cella in posizione x, y
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @param valore valore da inserire nella cella x, y
     */
    protected abstract void assegna(int x, int y, int valore);

    /**
     * deassegna il valore della cella in posizione x, y
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     */
    protected abstract void deassegna(int x, int y);

    /**
     *
     * @return la dimensione della griglia
     */
    protected abstract int dimensioneR();

    /**
     * risolve la griglia
     * @param controllaGabbie indica se le gabbie debbano essere controllate o meno
     */
    protected final void risolvi(boolean controllaGabbie)
    {
        ArrayList<Integer> scelte = scelte();
        /*
            quando il booleano è falso indica che sto assegnango dei valori temporanei alla griglia,
            quindi le gabbie non sono ancora state popolate correttamente
         */
        if (!controllaGabbie)
            Collections.shuffle(scelte); //viene fatto perchè altrimenti se i valori fossero sempre ordinati, si creerebbero valori di gabbie sempre uguali
        if (!risolviBT(controllaGabbie, 0, 0, scelte))
            throw new RuntimeException("Impossibile risolvere");
    }

    /**
     * implementa il backtracking su risolvi
     * @param controllaGabbie indica se le gabbie debbano essere controllate o meno
     * @param riga posizione di riga nella griglia
     * @param colonna posizione di colonna nella griglia
     * @param scelte i possibili valoi che possono essere assegnati ad una cella
     * @return true se esiste una soluzione per la griglia
     */
    private boolean risolviBT(boolean controllaGabbie, int riga, int colonna, ArrayList<Integer> scelte)
    {
        int prossimaColonna = (colonna + 1) % dimensioneR();
        int prossimaRiga = prossimaColonna == 0 ? riga + 1 : riga;
        if (riga < dimensioneR())
        {
            for (Integer valore : scelte)
                if (assegnabile(riga, colonna, valore, controllaGabbie))
                {
                    assegna(riga, colonna, valore);
                    if (risolviBT(controllaGabbie, prossimaRiga, prossimaColonna, scelte))
                        return true;
                    deassegna(riga, colonna);
                }
            return false;
        }
        else
            return true;
    }
}
