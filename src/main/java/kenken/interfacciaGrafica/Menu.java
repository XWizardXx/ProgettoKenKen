package kenken.interfacciaGrafica;

import kenken.griglia.DIFFICOLTA;
import kenken.griglia.grigliaCompleta.GrigliaCompletaImpl;
import kenken.salvataggio.Salvataggio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame
{
    JButton nuovaPartita;
    JButton caricaPartita;
    GrigliaCompletaImpl g;

    public Menu()
    {
        super("Menu");
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        nuovaPartita = new JButton("Nuova partita");
        nuovaPartita.setBounds(70, 20, 150, 30);
        add(nuovaPartita);
        nuovaPartita.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new NuovaPartita();
                setVisible(false);
            }
        });

        caricaPartita = new JButton("Carica partita");
        caricaPartita.setBounds(70, 60, 150, 30);
        caricaPartita.addActionListener(e ->
        {
            carica();
        });

        add(caricaPartita);

        setVisible(true);
    }

    private void carica()
    {
       try
       {
           g = new GrigliaCompletaImpl(DIFFICOLTA.FACILE, 1); //questa griglia viene creata solo per ripristinarla con il memento
           g.ripristina(Salvataggio.carica());
           new Partita(g);
           setVisible(false);
       }
       catch (RuntimeException e)
       {
           JOptionPane.showMessageDialog(null, "Nessun salvataggio trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
       }
    }
}
