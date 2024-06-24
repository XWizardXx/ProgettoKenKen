package kenken.gabbia;

import java.util.*;

public enum SEGNO
{
    SOMMA, SOTTRAZI0NE, MOLTIPLICAZIONE, DIVISIONE;

    private static final List<SEGNO> valori = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int size = valori.size();
    private static final Random random = new Random();

    public static SEGNO SegnoRandom()
    {
        return valori.get(random.nextInt(size));
    }
}
