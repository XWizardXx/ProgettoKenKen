package kenken.interfacciaGrafica;

import kenken.cella.Cella;
import kenken.gabbia.GabbiaImpl;
import kenken.griglia.grigliaCompleta.GrigliaCompletaImpl;
import kenken.patterns.memento.Memento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class SchermataSoluzioni extends JFrame
{
    private final GrigliaCompletaImpl g;
    private final LinkedList<Memento> soluzioni;
    private int cont = 0;

    public SchermataSoluzioni(GrigliaCompletaImpl g)
    {
        super("Soluzioni");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.g = g;
        soluzioni = g.listaSoluzioni();
        g.ripristina(soluzioni.getFirst());

        setLayout(new GridLayout(g.dimensione(), g.dimensione()));

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

        JPopupMenu pm = new JPopupMenu();
        JMenuItem avanti = new JMenuItem("Avanti");
        pm.add(avanti);
        avanti.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    g.ripristina(soluzioni.get(cont - 1));
                }
                catch (IndexOutOfBoundsException x)
                {
                    JOptionPane.showMessageDialog(null, "Non ci sono soluzioni prima di questa");
                }
            }
        });

        JMenuItem indietro = new JMenuItem("Indietro");
        pm.add(indietro);
        indietro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    g.ripristina(soluzioni.get(cont + 1));
                }
                catch (IndexOutOfBoundsException x)
                {
                    JOptionPane.showMessageDialog(null, "Non ci sono soluzioni dopo di questa");
                }
            }
        });

        pm.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                    pm.show(SchermataSoluzioni.this, e.getX(), e.getY());
            }
        });

        setVisible(true);
    }
}
