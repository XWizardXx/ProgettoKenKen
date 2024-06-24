package kenken.patterns.memento;

public interface Originator
{
    Memento salva();

    void ripristina(Memento m);
}
