public class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {

    @Override
    public void leggTil(T x) {
        Node temp = start;
        int teller = 0;

        if (start == null) {
            super.leggTil(x);
            return;
        }
        while (teller < stoerrelse() && x.compareTo(temp.data) > 0) {
            teller++;
            temp = temp.neste;
        }
        if (teller < stoerrelse()) {
            super.leggTil(teller, x);
        } else {
            super.leggTil(x);
        }
    }

    @Override
    public T fjern() throws UgyldigListeIndeks {
        return fjern(stoerrelse()-1);
    }

    @Override
    public void sett(int pos, T x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leggTil(int pos, T x) {
        throw new UnsupportedOperationException();
    }
}
