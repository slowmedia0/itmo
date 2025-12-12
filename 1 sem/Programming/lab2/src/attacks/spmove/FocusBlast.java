package attacks.spmove;

import ru.ifmo.se.pokemon.*;

public final class FocusBlast extends SpecialMove {
    public FocusBlast(){
        super(Type.FIGHTING, 120.0,70.0);
    }
    @Override
    protected void applyOppEffects(Pokemon var1) {
        Effect e = new Effect().chance(0.1).stat(Stat.SPECIAL_DEFENSE,-1);
        var1.addEffect(e);
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
