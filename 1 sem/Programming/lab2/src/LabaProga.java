import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class LabaProga {
    static public void main (String[] args){
        Battle b = new Battle();
        Sawsbuck p1 = new Sawsbuck("Sawsbuck", 20);
        Deerling p2 = new Deerling("Deerling", 1);
        Pheromosa p3 = new Pheromosa("Pheromosa",35);
        Pichu p4 = new Pichu("Pichu",10);
        Pikachu p5 = new Pikachu("Pikachu",10);
        Raichu p6 = new Raichu("Raichu",30);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
