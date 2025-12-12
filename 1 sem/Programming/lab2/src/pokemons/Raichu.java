package pokemons;
import attacks.spmove.FocusBlast;
public final class Raichu extends Pikachu {
    public Raichu (String var1, int var2) {
        super(var1,var2);
        this.setStats(60.0,90.0,55.0,90.0,80.0,110.0);
        this.addMove(new FocusBlast());
    }
}
