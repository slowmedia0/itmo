package attacks.pmove;

import ru.ifmo.se.pokemon.*;

public final class HornLeech extends PhysicalMove {
    public HornLeech(){
        super(Type.GRASS, 75.0,100.0);
    }
    @Override
    protected void applySelfEffects(Pokemon var1) {
        var1.setMod(Stat.HP,-(int)(0.5*(var1.getStat(Stat.HP)-var1.getHP())));
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
