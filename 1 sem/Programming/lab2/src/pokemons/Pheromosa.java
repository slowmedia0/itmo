package pokemons;
import attacks.pmove.PoisonJab;
import attacks.pmove.PowerUpPunch;
import attacks.pmove.RockTomb;
import attacks.stmove.BulkUp;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
public final class Pheromosa extends Pokemon {
    public Pheromosa(String var1, int var2) {
        super(var1,var2);
        this.setType(Type.BUG, Type.FIGHTING);
        this.setStats(71.0,137.0,37.0,137.0,37.0,151.0);
        this.setMove(new PowerUpPunch(),new PoisonJab(), new BulkUp(),new RockTomb());
    }
}
