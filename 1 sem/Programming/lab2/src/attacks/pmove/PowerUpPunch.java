package attacks.pmove;

import ru.ifmo.se.pokemon.*;

public final class PowerUpPunch extends PhysicalMove {
    public PowerUpPunch(){
        super(Type.FIGHTING, 40.0, 100.0);
    }
    @Override
    protected void applySelfEffects(Pokemon var1) {
        var1.setMod(Stat.ATTACK,1);
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
