package pokemons;
import attacks.pmove.Facade;
import attacks.stmove.Confide;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
public class Pichu extends Pokemon {
    public Pichu(String var1, int var2) {
        super(var1,var2);
        this.setType(Type.ELECTRIC);
        this.setStats(20.0,40.0,15.0,35.0,35.0,60.0);
        this.setMove(new Confide(), new Facade(this));
    }
}
