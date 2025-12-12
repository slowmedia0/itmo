package attacks.pmove;

import ru.ifmo.se.pokemon.*;

public final class Facade extends PhysicalMove {
    public Facade(Pokemon var1){
        super(Type.NORMAL,70.0,100.0);
        if ((var1.getCondition()== Status.BURN) || (var1.getCondition()== Status.POISON) || (var1.getCondition()== Status.PARALYZE)){
            this.power=140;
        }
    }
    @Override
    protected String describe(){
        return "does " + this.getClass().getSimpleName();
    }
}
