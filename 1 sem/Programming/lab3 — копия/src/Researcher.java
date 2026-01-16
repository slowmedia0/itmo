import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

abstract class Researcher implements Base {
    protected final String name;
    protected final boolean knowsNecronomicon;
    protected boolean isTraitor = false;
    protected double ambition;
    protected final Random rand = new Random();

    protected Researcher(String name) {
        this.name = name;
        this.knowsNecronomicon = rand.nextDouble() < 0.5;
        this.ambition = 20 + rand.nextInt(30);
    }

    public String getName() { return name; }
    public boolean knowsNecronomicon() { return knowsNecronomicon; }
    public boolean getIsTraitor() { return isTraitor; }
    public double getAmbition() { return ambition; }

    public void increaseAmbition(double amount) {
        this.ambition = Math.min(100, this.ambition + amount);
    }
    public void becomeTraitor() {
        this.isTraitor = true;
    }
    public abstract String reactToSpecimen(Specimen specimen);

    public void attemptToSteal(List<Specimen> specimens) {
        if (!isTraitor || specimens.isEmpty()) return;

        List<Specimen> available = new ArrayList<>();
        for (Specimen s : specimens) {
            if (s.getIsCollected() && !s.getIsStolen()) {
                available.add(s);
            }
        }
        if (available.isEmpty()) {
            throw new IllegalStateException("Нет образцов для кражи!");
        }

        int specimensToSteal = Math.min(1 + rand.nextInt(3), available.size());
        System.out.println("\n" + name + " Пытается сбежать с артефактами!");
        System.out.println("Он хочет украсть " + specimensToSteal + " особей!");

        Collections.shuffle(available, rand);
        int stolenCount = 0;
        for (int i = 0; i < specimensToSteal; i++) {
            available.get(i).setIsStolen(true);
            stolenCount++;
            System.out.println("Украдена: " + available.get(i).getName());
        }

        System.out.println("Всего украдено: " + stolenCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Researcher that = (Researcher) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + (knowsNecronomicon ? "[Know]" : "") + (isTraitor ? "[Traitor]" : "") + "[" + ambition*100 + "%]";
    }
}