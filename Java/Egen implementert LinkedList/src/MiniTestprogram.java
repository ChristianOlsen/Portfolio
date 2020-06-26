public class MiniTestprogram {

    public static void main(String[] args) {

        Lenkeliste liste = new SortertLenkeliste();
        liste.leggTil("C");
        liste.leggTil("D");
        liste.leggTil("A");
        liste.leggTil("B");

        liste.skrivUt();
    }
}
