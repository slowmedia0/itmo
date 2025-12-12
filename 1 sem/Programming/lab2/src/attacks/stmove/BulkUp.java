package attacks.stmove;
import ru.ifmo.se.pokemon.*;

public final class BulkUp extends StatusMove {
    public BulkUp(){
        super(Type.FIGHTING,0.0,0.0);
    }
    @Override
    protected void applySelfEffects(Pokemon var1) {
        var1.setMod(Stat.ATTACK,1);
        var1.setMod(Stat.DEFENSE,1);
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
