package attacks.pmove;
import ru.ifmo.se.pokemon.*;

public final class RockTomb extends PhysicalMove {
    public RockTomb()
    {
        super(Type.ROCK, 60.0, 95.0);
    }
    @Override
    protected void applyOppEffects(Pokemon var1) {
        var1.setMod(Stat.SPEED,-1);
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
