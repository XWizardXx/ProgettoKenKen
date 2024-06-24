package kenken.salvataggio;

import kenken.patterns.memento.Memento;

import java.io.*;

public class Salvataggio
{
    private static final File salvataggio;

    static
    {
        salvataggio = new File("salvataggio.dat");
        try
        {
            boolean s = salvataggio.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void salva(Memento m)
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(salvataggio));
            out.writeObject(m);
            out.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Memento carica()
    {
        Memento ret;
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(salvataggio));
            Object o = in.readObject();
            if (!(o instanceof Memento))
                throw new RuntimeException("Salvataggio non trovato");
            in.close();
            ret = (Memento) o;
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return ret;
    }
}
