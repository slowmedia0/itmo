package attacks.stmove;

import ru.ifmo.se.pokemon.*;

public final class Growl extends StatusMove {
    public Growl(){
        super(Type.NORMAL, 0.0, 100.0);
    }
    @Override
    protected void applyOppEffects(Pokemon var1) {
        var1.setMod(Stat.ATTACK,-1);
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
