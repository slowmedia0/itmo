package pokemons;
import attacks.pmove.DoubleKick;
import attacks.stmove.Confide;
import attacks.stmove.Growl;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
public class Deerling extends Pokemon {
    public Deerling(String var1, int var2) {
        super(var1, var2);
        this.setType(Type.NORMAL, Type.GRASS);
        this.setStats(60.0, 60.0, 50.0, 40.0, 50.0, 75.0);
        this.setMove(new DoubleKick(), new Confide(), new Growl());
    }
}
