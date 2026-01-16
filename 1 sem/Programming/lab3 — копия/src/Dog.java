import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Dog {
    private final String name;
    private boolean isUnderControl = true;
    private final Random rand = new Random();

    public Dog(String name) {
        this.name = name;
    }

    public void reactToSpecimens(List<Specimen> specimens) {
        int k=0;
        for (Specimen s : specimens) {
            if (s.getIsCollected() && !s.getIsStolen() && !s.getIsDamaged())
            {k++;}
        }
        if (k>0 && rand.nextDouble() < 0.5) {
            String[] messages = {"хрипит от лая", "яростно лает", "беспокоится у находок", "громко воет"};
            System.out.println(name + " " + messages[rand.nextInt(messages.length)] + "!");
        }
    }

    public boolean tryToBreakFree() {
        if (isUnderControl && rand.nextDouble() < 0.5) {
            isUnderControl = false;
            String[] messages = {"СОРВАЛСЯ С ПОВОДКА!", "ВЫРВАЛСЯ ИЗ-ПОД КОНТРОЛЯ!", "ПРОРВАЛСЯ К НАХОДКАМ!"};
            System.out.println("\n" + name + " " + messages[rand.nextInt(messages.length)]);
            return true;
        }
        return false;
    }

    public void damageSpecimens(List<Specimen> specimens) {
        if (!isUnderControl && !specimens.isEmpty()) {
            int damageAttempts = 1 + rand.nextInt(3);
            int damagedCount = 0;

            List<Specimen> targets = new ArrayList<>();
            for (Specimen s : specimens) {
                if (s.getIsCollected() && !s.getIsStolen() && !s.getIsDamaged())
                {targets.add(s);}
            }
            Collections.shuffle(targets, rand);
            for (int i = 0; i < Math.min(damageAttempts, targets.size()); i++) {
                if (rand.nextDouble() < 0.5) {
                    if (targets.get(i).damageByDog()==true){
                        damagedCount++;}
                }
            }

            if (damagedCount > 0) {
                System.out.println("Повреждено артефактов: " + damagedCount);
            } else {
                System.out.println("Удалось вовремя остановить собаку!");
            }
            isUnderControl = true;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Dog that = (Dog)o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + this.hashCode();
    }
}