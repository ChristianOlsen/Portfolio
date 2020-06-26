public class SortertLenkeliste<T extends Comparable<T> > extends Lenkeliste<T> {
    public SortertLenkeliste() {
        //legger til fra LenkeListe
        super();
    }
//metodene lages hvor man tar i utgangspunkt at listen allerede er sortert
    @Override
    public void leggTil(T x) {
        Node<T> p = start;
        int teller = 0;
        //sjekker om listen er tom
        if (stoerrelse() == 0) {
            super.leggTil(x);
        } else {
            //går igjennom listen og sammenligner, og øker indeks
            while (teller < stoerrelse() && x.compareTo(p.data) > 0) {
                teller++;
                p = p.neste;
                }
            if (teller < stoerrelse()) {
                super.leggTil(teller, x);
            } else {
                super.leggTil(x);
            }
        }
    }

    @Override
    public T fjern() throws UgyldigListeIndeks {
        //fjerner siste element i listen
        if (stoerrelse() > 0) {
            return super.fjern(stoerrelse() - 1);
        }  else {
            throw new UgyldigListeIndeks(stoerrelse());
        }
    }
    //overrider metodene sett og leggTil til UnsopportedOperationException
    @Override
    public void sett(int pos, T x) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void leggTil(int pos, T x) {
        throw new UnsupportedOperationException();
    }
}