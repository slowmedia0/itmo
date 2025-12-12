package attacks.stmove;
import ru.ifmo.se.pokemon.*;

public final class Confide extends StatusMove {
    public Confide(){
        super(Type.NORMAL,0.0,0.0);
    }
    @Override
    protected void applyOppEffects(Pokemon var1) {
        var1.setMod(Stat.SPECIAL_ATTACK,-1);
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
