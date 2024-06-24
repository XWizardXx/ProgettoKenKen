package kenken.griglia.grigliaCompleta;

import kenken.cella.Cella;
import kenken.griglia.griglia3x3.Griglia3x3Impl;
import kenken.patterns.memento.Memento;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public interface GrigliaCompleta extends Serializable
{
    /**
     * inserisce il valore val nella cella in posizione x, y
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @param val valore da inserire nella cella x, y
     */
    default void inserisciValore(int x, int y, int val)
    {
        int[] coord = convertiCoordinate(x, y);
        dammiGriglia3x3(coord[0], coord[1]).inserisciVal(coord[2], coord[3], val);
    }

    /**
     * rimuove il valore dalla cella in posizione x, y
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     */
    default void rimuoviValore(int x, int y)
    {
        int[] coord = convertiCoordinate(x, y);
        dammiGriglia3x3(coord[0], coord[1]).rimuoviVal(coord[2], coord[3]);
    }

    /**
     *
     * @return una linked list contenente le soluzioni della griglia
     */
    LinkedList<Memento> listaSoluzioni();

    /**
     *
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @param dim dimensione della griglia
     * @return true se la posizione della cella x, y è valida
     */
    default boolean posizioneValida(int x, int y, int dim)
    {
        return x >= 0 && y >= 0 && x < dim && y < dim;
    }



    /**
     *
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @return la cella in posizione x, y
     * @throws NoSuchElementException se la posizione della cella non è valida nella griglia
     */
    Cella dammiCella(int x, int y) throws NoSuchElementException;

    /**
     *
     * @param x posizione di riga nella griglia
     * @return la riga x della griglia
     */
    LinkedList<Cella> dammiRiga(int x);

    //restituisce la colonna y

    /**
     *
     * @param y posizione di colonna nella grilia
     * @return la colonna y della griglia
     */
    LinkedList<Cella> dammiColonna(int y);

    /**
     *
     * @param i posizione di riga nella griglia
     * @param j posizione di colonna nella griglia
     * @return la Griglia3x3 in posizione i, j
     * @throws NoSuchElementException se la posizione della Griglia3x3 non è valida
     */
    Griglia3x3Impl dammiGriglia3x3(int i, int j) throws NoSuchElementException;

    /**
     *
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @return un array di interi contenente le coordinate della griglia3x3 e della cella cosrrispondenti ad x e y
     */
    default int[] convertiCoordinate(int x, int y)
    {
        int[] ret = new int[4];
        if (!posizioneValida(x, y, dimensione()))
            throw new NoSuchElementException("posizione non valida");
        int i = -1;
        int j = -1;
        int a = -1;
        int b = -1;
        if (x >= 0 && x <= 2)
            i = 0;
        else if (x >= 3 && x <= 5)
            i = 1;
        else if (x >= 6 && x <= 8)
            i = 2;
        if (y >= 0 && y <= 2)
            j = 0;
        else if (y >= 3 && y <= 5)
            j = 1;
        else if (y >= 6 && y <= 8)
            j = 2;
        switch (x)
        {
            case 0, 3, 6 -> a = 0;
            case 1, 4, 7 -> a = 1;
            case 2, 5, 8 -> a = 2;
        }
        switch (y)
        {
            case 0, 3, 6 -> b = 0;
            case 1, 4, 7 -> b = 1;
            case 2, 5, 8 -> b = 2;
        }
        ret[0] = i;
        ret[1] = j;
        ret[2] = a;
        ret[3] = b;
        return ret;
    }

    /**
     *
     * @return la dimensione della GrigliaCompleta
     */
    int dimensione();

    /**
     *
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @param valore valore assegnato alla cella x, y
     * @return true se il valore nella cella x, y è stato assegnato correttamente nelle gabbie
     */
    boolean controllaGabbie(int x, int y, int valore);

    /**
     *
     * @param x posizione di riga nella griglia
     * @param y posizione di colonna nella griglia
     * @param valore valore assegnato alla cella x, y
     * @return true se il valore nella cella x, y è stato assegnato correttamente
     */
    boolean controlla(int x, int y, int valore);
}
