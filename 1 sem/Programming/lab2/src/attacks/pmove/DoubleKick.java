package attacks.pmove;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public final class DoubleKick extends PhysicalMove {
    public DoubleKick(){
        super(Type.FIGHTING,30.0,100.0);
        this.hits=2;
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
