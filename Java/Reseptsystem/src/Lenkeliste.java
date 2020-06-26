//import java.util.Iterator;
public class Lenkeliste<T> implements Liste<T> {

    //hvorfor fungerer ikke dette??
    @Override
    public LenkeListeIterator<T> iterator() {
        return new LenkeListeIterator(this);
    }

    //oppretter nodeklasse
    public class Node<T> {
        //instansvariabler - "neste" er peker og "data" er <T>
        Node<T> neste = null;
        T data;
        //konstruktør til Node
        Node(T x) {
            data = x;
        }
    }
    //Start Node, første peker
    public Node<T> start = null;

    @Override
    public int stoerrelse() {
        //går gjennom hele listen og teller alle nodene:
        Node p = start;
        if (p != null) {
            int n = 0;
            while (p != null) {
                n++;
                p = p.neste;
            }
            return n;
        } else {
            return 0;
        }
    }

    @Override
    public void leggTil(int pos, T x) throws UgyldigListeIndeks {
        //lager ny node med <T> verdien x
        Node<T> ny = new Node<T>(x);
        Node<T> p = start;
        //sjekker om pos er gyldig
        if (pos <= stoerrelse() && pos >= 0) {
            if (stoerrelse() == 0)  {
                start = ny;
            } else if (pos == 0) {
                ny.neste = start;
                start = ny;
            } else if (pos == stoerrelse()) {
                leggTil(x);
            } else {
                //går igjennom listen til plassen ny node skal plasseres
                Node<T> n = p.neste;
                for (int i = 1; i < pos; i++) {
                    p = p.neste;
                    n = p.neste;
                }
                //plasserer node
                ny.neste = n;
                p.neste = ny;
            }

        } else { //Hvis ugyldig posisjon gjør dette
            throw new UgyldigListeIndeks(pos);
        }
    }

    @Override
    public void leggTil(T x) {
        if (start != null) {
            //Vi må først telle oss frem til det siste elementet i listen:
            Node p = start;
            while (p.neste != null) {
                p = p.neste;
            }
            p.neste = new Node<T>(x);
        } else {
            start = new Node<T>(x);
        }
    }

    @Override
    public void sett(int pos, T x) throws UgyldigListeIndeks {
        //finner det aktuelle elementet i listen:
        //sjekker om gyldig pos
        if (pos >= 0 && pos < stoerrelse()) {
            Node p = start;
            for (int i = 0; i < pos; i++) {
                p = p.neste;
            }
            //setter verdien <T> x til posisjonen
            p.data = x;
        } else {
            throw new UgyldigListeIndeks(pos);
        }
    }

    @Override
    public T hent(int pos) throws UgyldigListeIndeks{
        //teller seg fram til den riktige noden:
        //sjekker om gyldig pos
        if (pos >= 0 && pos < stoerrelse()) {
            Node<T> p = start;
            for (int i = 0; i < pos; i++) {
                p = p.neste;
            }
            return p.data;
        } else {
            throw new UgyldigListeIndeks(pos);
        }
    }

    @Override
    public T fjern(int pos) throws UgyldigListeIndeks {
        //finner posisjon og fjerner peker for noden, samt returnerer verdien <T> til fjernet node
        //sjekker om gyldig pos
        if (pos < stoerrelse() && pos >= 0) {
            //hvis stoerrelsen er 1
            if (stoerrelse() == 1) {
                Node<T> n = start;
                start = null;
                return n.data;
            } else {
                //teller fram til elementet før det som skal fjernes:
                Node<T> p = start;
                Node<T> q = start.neste;

                for (int i = 1; i < pos; i++) {
                    p = p.neste;
                    q = p.neste;
                }
                p.neste = q.neste;
                return q.data;
            }
        } else {
            throw new UgyldigListeIndeks(pos);
        }
    }

    @Override
    public T fjern() throws UgyldigListeIndeks {
        //fjerner første element i listen
        if (start != null) {
            Node<T> p = start;
            start = start.neste;
            return p.data;
        } else {
            throw new UgyldigListeIndeks(0);
        }
    }
}
