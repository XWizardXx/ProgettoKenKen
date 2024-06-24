package kenken.interfacciaGrafica;

import kenken.griglia.DIFFICOLTA;
import kenken.griglia.grigliaCompleta.GrigliaCompletaImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuovaPartita extends JFrame
{
    public NuovaPartita()
    {
        super("Nuova partita");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JLabel introduzione = new JLabel("Scegli le impostazioni della partita");
        introduzione.setBounds(100, 10, 300, 20);
        add(introduzione);

        JButton inizia = new JButton("Inizia");
        inizia.setBounds(145, 50, 100, 50);
        add(inizia);

        //-------------------------------// selezione difficoltà
        JLabel testoDifficolta = new JLabel("Difficoltà");
        testoDifficolta.setBounds(30, 30, 100, 20);
        add(testoDifficolta);

        ButtonGroup gruppoDifficolta = new ButtonGroup();

        JRadioButton facile = new JRadioButton("Facile");
        gruppoDifficolta.add(facile);
        facile.setBounds(30, 60, 100, 20);
        add(facile);

        JRadioButton medio = new JRadioButton("Medio");
        gruppoDifficolta.add(medio);
        medio.setBounds(30, 80, 100, 20);
        add(medio);

        JRadioButton difficile = new JRadioButton("Difficile");
        gruppoDifficolta.add(difficile);
        difficile.setBounds(30, 100, 100, 20);
        add(difficile);

        //-------------------------------// selta numero delle soluzuioni da visualizzare
        JLabel testoSoluzioni = new JLabel("Soluzioni");
        testoSoluzioni.setBounds(300, 30, 100, 20);
        add(testoSoluzioni);

        SpinnerModel valori = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner nSoluzioni = new JSpinner(valori);
        nSoluzioni.setBounds(300, 60, 50, 20);
        add(nSoluzioni);

        inizia.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!facile.isSelected() && !medio.isSelected() && !difficile.isSelected())
                    JOptionPane.showMessageDialog(null, "Le impostazioni non possono rimanere vuote!", "Errore", JOptionPane.WARNING_MESSAGE);
                else
                {
                    DIFFICOLTA difficolta;
                    if (facile.isSelected())
                        difficolta = DIFFICOLTA.FACILE;
                    else if (medio.isSelected())
                        difficolta = DIFFICOLTA.MEDIO;
                    else
                        difficolta = DIFFICOLTA.DIFFICILE;
                    int nSol = (int) nSoluzioni.getValue();
                    GrigliaCompletaImpl g = new GrigliaCompletaImpl(difficolta, nSol);
                    new Partita(g);
                    setVisible(false);
                }
            }
        });

        setVisible(true);
    }
}
