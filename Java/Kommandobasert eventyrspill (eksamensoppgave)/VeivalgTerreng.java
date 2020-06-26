import java.io.File;


public class VeivalgTerreng extends Terreng {

    public VeivalgTerreng(File stederFil) {
        super(stederFil);
        settAntUtganger(3);
    }

    @Override
    public void leggTilSted(String beskrivelse) {
        leggTilSted(new VeivalgSted(beskrivelse));
    }
}
