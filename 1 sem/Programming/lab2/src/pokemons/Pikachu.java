package pokemons;
import attacks.stmove.PlayNice;
public class Pikachu extends Pichu {
    public Pikachu(String var1, int var2) {
        super(var1,var2);
        this.setStats(35.0,55.0,40.0,50.0,50.0,90.0);
        this.addMove(new PlayNice());
    }
}
