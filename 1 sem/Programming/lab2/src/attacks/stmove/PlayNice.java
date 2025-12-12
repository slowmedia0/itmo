package attacks.stmove;

import ru.ifmo.se.pokemon.*;

public final class PlayNice extends StatusMove {
    public PlayNice(){
        super(Type.NORMAL,0.0,0.0);
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
