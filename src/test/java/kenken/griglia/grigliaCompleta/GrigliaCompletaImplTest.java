package kenken.griglia.grigliaCompleta;

import kenken.griglia.DIFFICOLTA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrigliaCompletaImplTest
{
    @Test
    void verificaStampaCorretta()
    {
        GrigliaCompletaImpl g = new GrigliaCompletaImpl(DIFFICOLTA.MEDIO, 1);
        System.out.println(g);
        assertNotNull(g);
    }

    @Test
    void verificaInserimentoValore()
    {
        GrigliaCompletaImpl g = new GrigliaCompletaImpl(DIFFICOLTA.FACILE, 5);
        g.inserisciValore(0, 2, 9);
        assertEquals(9, g.grigliaCompleta[0][0].dammiCella(0, 2).getValore());
    }

    @Test
    void verifivaRimozioneValore()
    {
        GrigliaCompletaImpl g = new GrigliaCompletaImpl(DIFFICOLTA.FACILE, 5);
        g.inserisciValore(0, 2, 9);
        g.rimuoviValore(0 ,2);
        assertEquals(0, g.grigliaCompleta[0][0].dammiCella(0, 2).getValore());
    }

    @Test
    void verificaDammiRiga()
    {
        GrigliaCompletaImpl g = new GrigliaCompletaImpl(DIFFICOLTA.FACILE, 5);
        assertEquals(3, g.dammiRiga(2).size());
    }

    @Test
    void verificaDammiColonna()
    {
        GrigliaCompletaImpl g = new GrigliaCompletaImpl(DIFFICOLTA.FACILE, 5);
        assertEquals(3, g.dammiColonna(2).size());
    }
}