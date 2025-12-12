package attacks.pmove;
import ru.ifmo.se.pokemon.*;

public final class PoisonJab extends PhysicalMove {
    public PoisonJab(){
        super(Type.POISON, 80.0, 100.0);
    }
    @Override
    protected void applyOppEffects(Pokemon var1) {
        Effect e = new Effect().chance(0.3).condition(Status.POISON);
        var1.addEffect(e);
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
