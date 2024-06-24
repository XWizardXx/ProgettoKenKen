package kenken.interfacciaGrafica;

import kenken.cella.Cella;
import kenken.griglia.grigliaCompleta.GrigliaCompletaImpl;
import kenken.salvataggio.Salvataggio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Partita extends JFrame
{
    private final GrigliaCompletaImpl g;
    private boolean salvato = false;
    private boolean controlloAbilitato = true;

    public Partita(GrigliaCompletaImpl g)
    {
        super("Partita");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.g = g;
        setLayout(new GridLayout(g.dimensione(), g.dimensione()));

        //-------------------------------------------------// menu delle scelte
        JPopupMenu scelte = new JPopupMenu();
        JMenuItem[] elementiScelte = new JMenuItem[g.dimensione()];
        for (int i = 0; i < elementiScelte.length; i++)
        {
            JMenuItem mi = new JMenuItem("" + (i + 1));
            elementiScelte[i] = mi;
            scelte.add(mi);
        }
        add(scelte);

        //-------------------------------------------------// aggiunta bottoni al layout
        final int[] xy = new int[2];
        final JLabel[][] labels = new JLabel[g.dimensione()][g.dimensione()];
        for (int i = 0; i < g.dimensione(); i++)
            for (int j = 0; j < g.dimensione(); j++)
            {
                Cella c = g.dammiCella(i, j);
                JLabel b = new JLabel(c.getG().toString());
                b.setFont(new Font("Arial", Font.PLAIN, 20));
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(b);
                labels[i][j] = b;
            }


        this.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    scelte.show(Partita.this, e.getX(), e.getY());
                    xy[0] = e.getX();
                    xy[1] = e.getY();
                } else if (e.getButton() == MouseEvent.BUTTON3)
                {
                    xy[0] = e.getX();
                    xy[1] = e.getY();
                    rimuoviValore(xy[0], xy[1], labels);
                }
            }
        });

        //-------------------------------------------------// inserimento valore
        for (int i = 0; i < elementiScelte.length; i++)
        {
            JMenuItem corrente = elementiScelte[i];
            int I = i;
            corrente.addActionListener(e ->
            {
                inserisciValore(xy[0], xy[1], I + 1, labels);
            });
        }

        JMenu opzioni = new JMenu("Opzioni");
        JMenuItem aiuto = new JMenuItem("Aiuto");
        opzioni.add(aiuto);
        aiuto.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Per inserire un numero clicca con il tasto sinistro del mouse\nPer rimuoverlo clicca con il tasto destro del mouse", "Aiuto", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem salva = new JMenuItem("Salva");
        opzioni.add(salva);
        salva.addActionListener(e ->
        {
            salvato = true;
            salva();
        });

        JMenuItem soluzioni = new JMenuItem("Soluzioni");
        opzioni.add(soluzioni);
        soluzioni.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new SchermataSoluzioni(g);
            }
        });

        JMenuItem verifica = new JMenuItem("Verifica");
        opzioni.add(verifica);
        verifica.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                verificaFinito();
            }
        });

        JMenuItem home = new JMenuItem("Home");
        opzioni.add(home);
        home.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!salvato)
                    JOptionPane.showMessageDialog(null, "Non hai ancora salvato la partita", "Attento", JOptionPane.WARNING_MESSAGE);
                else
                {
                    new Menu();
                    setVisible(false);
                }
            }
        });

        JMenuItem abilitaControllo = new JMenuItem("Abilita controllo");
        opzioni.add(abilitaControllo);
        abilitaControllo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controlloAbilitato = !controlloAbilitato;
            }
        });

        JMenuBar menu = new JMenuBar();
        menu.add(opzioni);
        add(menu);
        setJMenuBar(menu);

        setVisible(true);
    }

    private void verificaFinito()
    {
        for (int i = 0; i < g.dimensione(); i++)
        {
            for (int j = 0; j < g.dimensione(); j++)
            {
                Cella c = g.dammiCella(i, j);
                if (c.getValore() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Non hai ancora riempito tutta la griglia!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!g.controlla(i, j, c.getValore()))
                {
                    JOptionPane.showMessageDialog(null, "Alcuni valori inseriti non sono corretti!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                    }
            }
        }
        int a = JOptionPane.showConfirmDialog(null, "Hai risolto la partita!\n Vuoi tornare alla home?", "Complimenti", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION)
        {
            new NuovaPartita();
            setVisible(false);
        }
    }

    private void salva()
    {
        Salvataggio.salva(g.salva());
        JOptionPane.showMessageDialog(null, "Salvataggio avvenuto con successo!", "Salvataggio", JOptionPane.INFORMATION_MESSAGE);
    }

    private void inserisciValore(int x, int y, int valore, JLabel[][] labels)
    {
        int r = Math.min(Math.floorDiv(y * g.dimensione(), 500), g.dimensione());
        int c = Math.min(Math.floorDiv(x * g.dimensione(), 500), g.dimensione());
        labels[r][c].setText(valore+g.dammiCella(r, c).getG().toString());
        if (controlloAbilitato && !g.controllaGabbie(r, c, valore))
            JOptionPane.showMessageDialog(null, "Il valore appena inserito non rispetta i vincoli della gabbia!", "Errore", JOptionPane.ERROR_MESSAGE);
        g.inserisciValore(r, c, valore);
        if (controlloAbilitato && !g.controlla(r, c, valore))
            JOptionPane.showMessageDialog(null, "Il valore appena inserito non rispetta i vincoli della griglia!", "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public void rimuoviValore(int x, int y, JLabel[][] labels)
    {
        int riga = Math.min(Math.floorDiv(y * g.dimensione(), 500), g.dimensione());
        int colonna = Math.min(Math.floorDiv(x * g.dimensione(), 500), g.dimensione());
        g.rimuoviValore(riga, colonna);
        labels[riga][colonna].setText(g.dammiCella(riga, colonna).getG().toString());
    }
}
