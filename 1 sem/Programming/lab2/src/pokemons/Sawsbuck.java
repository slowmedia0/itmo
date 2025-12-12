package pokemons;
import attacks.pmove.HornLeech;

public final class Sawsbuck extends Deerling {
    public Sawsbuck(String var1, int var2) {
        super(var1,var2);
        this.setStats(80.0,100.0,70.0,60.0,70.0,95.0);
        this.addMove(new HornLeech());
    }
}
