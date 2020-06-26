import java.util.Iterator;
import java.util.function.Consumer;

public class LenkeListeIterator<T> implements Iterator<T> {

    private Liste<T> minListe;
    private int indeks = 0;

    public LenkeListeIterator(Liste<T> liste) {
         minListe = liste;
    }
    //fiks om oppstår probelemr?
    @Override
    public boolean hasNext() {
        return indeks < minListe.stoerrelse();
    }
    @Override
    public T next() {
        return minListe.hent(indeks++);
    }

    @Override
    public void remove() {

    }
}
