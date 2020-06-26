public class Lenkeliste<T> implements Liste<T> {

    Node start;

    public class Node {
        Node neste = null;
        T data;

        Node(T x) {
            data = x;
        }
    }

    @Override
    public int stoerrelse() {
        int ant = 0;
        Node temp = start;
        while (temp != null) {
            ant++;
            temp = temp.neste;
        }
        return ant;
    }

    @Override
    public void leggTil(int pos, T x) throws UgyldigListeIndeks {
        Node nyNode = new Node(x);
        Node temp = start;
        if (pos == stoerrelse()) {
            leggTil(x);
            return;
        }
        if (gyldigIndeks(pos)) {
            if (start == null) {
                start = nyNode;
            } else if (pos == 0) {
                nyNode.neste = start;
                start = nyNode;
            } else {
                Node etterTemp = temp.neste;
                for (int i = 1; i < pos; i++) { // første indeks er sjekket så vi looper fra 1
                    temp = temp.neste;
                    etterTemp = temp.neste;
                }
                nyNode.neste = etterTemp;
                temp.neste = nyNode;
            }
        } else {
            throw new UgyldigListeIndeks(pos);
        }
    }

    @Override
    public void leggTil(T x) {
        Node nyNode = new Node(x);
        Node temp = start;
        if (start == null) {
            start = nyNode;
        } else {
            while (temp.neste != null) {
                temp = temp.neste;
            }
            temp.neste = nyNode;
        }
    }

    @Override
    public void sett(int pos, T x) throws UgyldigListeIndeks{
        if (gyldigIndeks(pos)) {
            Node temp = start;
            for (int i=0; i<pos; i++) {
                temp = temp.neste;
            }
            temp.data = x;
        } else {
            throw new UgyldigListeIndeks(pos);
        }
    }

    @Override
    public T hent(int pos) throws UgyldigListeIndeks{
        if (gyldigIndeks(pos)) {
            Node temp = start;
            for (int i=0; i<pos; i++) {
                temp = temp.neste;
            }
            return temp.data;
        }
        throw new UgyldigListeIndeks(pos);
    }

    @Override
    public T fjern(int pos) throws UgyldigListeIndeks {
        if (gyldigIndeks(pos)) {
            if (stoerrelse() == 1) {
                Node fjern = start;
                start = null;
                return fjern.data;
            } else {
                Node temp = start;
                Node fjern = start.neste;

                for (int i = 1; i < pos; i++) {
                    temp = temp.neste;
                    fjern = temp.neste;
                }
                temp.neste = fjern.neste;
                return fjern.data;
            }
        }
        throw new UgyldigListeIndeks(pos);
    }

    @Override
    public T fjern() throws UgyldigListeIndeks{
        if (start != null) {
            Node fjerne = start;
            start = start.neste;
            return fjerne.data;
        }
        throw new UgyldigListeIndeks(0);
    }

    private boolean gyldigIndeks(int pos) {
        return pos >= 0 && pos < stoerrelse();
    }

    public void skrivUt() {
        if (start == null) {
            System.out.println("Listen er tom");
            return;
        }
        Node temp = start;
        while (temp.neste != null) {
            System.out.println(temp.data);
            temp = temp.neste;
        }
        System.out.println(temp.data);
    }
}
