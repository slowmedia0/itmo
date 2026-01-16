import java.util.List;
import java.util.Random;

class Storyteller extends OrdinaryResearcher {
    private final List<OrdinaryResearcher> team;
    private final Random rand = new Random();

    public Storyteller(String name, List<OrdinaryResearcher> team) {
        super(name);
        this.team = team;
    }

    public void triggerFlashbacks(int collectedSpecimens) {
        int k = 0;
        for (Researcher r : team) {
            if (r.understandsElderThings()) {
                k++;
                r.increaseAmbition(15);
            }
        }
        if (rand.nextDouble() < 0.5) {
            System.out.println("«Неповрежденные особи так напоминают некоторых существ из древней мифологии,\nчто нельзя не предположить, что когда-то они обитали вне Антарктики.»");
            if (k==3) {
                System.out.println("\n«Дайер и Пэбоди читали 'Некрономикон', видели жуткие рисунки\nвдохновленного им Кларка Эштона Смита и потому понимают меня\nкогда я говорю о Старцах — тех, которые якобы породили\nжизнь на Земле не то шутки ради, не то по ошибке.»");
                if (collectedSpecimens >= 7) {
                    System.out.println("\n«Вроде чудовищ из доисторического фольклора,\nо которых писал Уилмарт. Вспоминается культ Ктулху…»");
                    increaseAmbition(10);
                }
            }
        }
    }
}