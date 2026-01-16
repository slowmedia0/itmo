import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Expedition {
    private final List<OrdinaryResearcher> researchers = new ArrayList<>();
    private final List<Specimen> specimens = new ArrayList<>();
    private final List<Dog> dogs = new ArrayList<>();
    private final Cave cave = new Cave();
    private final Random rand = new Random();
    private int collectedCount = 0;
    private boolean betrayalOccurred = false;
    private final int n = 1 + rand.nextInt(25);

    public Expedition() {
        researchers.add(new OrdinaryResearcher("Дайер"));
        researchers.add(new OrdinaryResearcher("Пэбоди"));
        researchers.add(new Storyteller("Рассказчик", researchers));

        dogs.add(new Dog("Рекс"));
        dogs.add(new Dog("Белка"));
        dogs.add(new Dog("Шарик"));
        System.out.println("\nТекущие амбиции команды:");
        for (Researcher r : researchers) {
            System.out.println("  " + r.getName() + ": " + r.getAmbition() + "%");
        }
    }
    public void discoverAndCollect() {
        System.out.println("Обнаружены особи!");
        for (int i = 1; i <= n; i++) {
            specimens.add(new Specimen("Особь-" + i, rand.nextDouble() < 0.5 ? GeologicalPeriod.LATE_CRETACEOUS : GeologicalPeriod.EARLY_EOCENE));
        }

        collectedCount = 0;
        for (Specimen specimen : specimens) {
            OrdinaryResearcher randomResearcher = researchers.get(rand.nextInt(researchers.size()));
            if (specimen.tryToCollect()) {
                collectedCount++;
            }
            System.out.println(randomResearcher.getName() + ": " + randomResearcher.reactToSpecimen(specimen));
        }

        System.out.println("\nСобрано особей: " + collectedCount + " из " + n);
        System.out.println("Главное — уберечь их от собак!");
        if (collectedCount > 0) {
            ((Storyteller)researchers.get(2)).triggerFlashbacks(collectedCount);
            checkForPotentialBetrayal();
        } else {
            System.out.println("\nНе удалось извлечь ни одного артефакта!");
        }
    }

    private void checkForPotentialBetrayal() {
        for (Researcher researcher : researchers) {
            if (rand.nextDouble() < 0.5) {
                researcher.becomeTraitor();
                System.out.println(researcher.getName() + " решает предать команду!");
                betrayalOccurred = true;
                break;
            }
        }
    }

    public void handleDogControl() {
        int availableCount = 0, stolenCount = 0, damagedCount = 0;
        for (Specimen s : specimens) {
            if (s.getIsCollected() && !s.getIsStolen() && !s.getIsDamaged()) availableCount++;
            if (s.getIsStolen()) stolenCount++;
            if (s.getIsDamaged()) damagedCount++;
        }
        boolean anyDogRampaged = false;
        for (Dog dog : dogs) {
            dog.reactToSpecimens(specimens);
            if (availableCount>0 && dog.tryToBreakFree()) {
                anyDogRampaged = true;
                dog.damageSpecimens(specimens);
            }
        }

        if (anyDogRampaged) {
            System.out.println("\nСобак нужно срочно убрать от особей!");
        }
    }

    public void handleBetrayal() {
        if (!betrayalOccurred) return;

        for (Researcher researcher : researchers) {
            if (researcher.getIsTraitor()) {
                try {
                    researcher.attemptToSteal(specimens);
                } catch (IllegalStateException e) {
                    System.out.println("Предатель не смог украсть: " + e.getMessage());
                }
                System.out.println("\nРеакция команды:");
                for (Researcher other : researchers) {
                    if (!other.getIsTraitor()) {
                        System.out.println(other.getName() + ": \"Остановите его!\"");
                    }
                }

                collectedCount = 0;
                for (Specimen s : specimens) {
                    if (s.getIsCollected() && !s.getIsStolen()) collectedCount++;
                }
                System.out.println("\nПосле кражи осталось особей: " + collectedCount);
                break;
            }
        }
    }

    public void prepareForExit() throws ExpeditionException {
        System.out.println("\nПодготовка к выходу:");

        handleDogControl();

        int damagedCount = 0;
        for (Specimen s : specimens) {
            if (s.getIsDamaged()) damagedCount++;
        }

        if (damagedCount > 0) {
            System.out.println("\n" + damagedCount + " особей повреждены собаками!");
            if (damagedCount >= 7 && rand.nextDouble() < 0.5) {
                throw new ExpeditionException("Собаки повредили слишком много находок!");
            }
        }

        if (cave.checkForCollapse()) {
            System.out.println("\nВыход заблокирован обвалом!");
            boolean collapseResolved = cave.handleCollapse();
            if (!collapseResolved) {
                throw new ExpeditionException("Не удалось расчистить завал!");
            }
        }
    }

    public boolean attemptExit() {
        System.out.println("\nПопытка выхода:");

        if (!cave.canExit()) {
            System.out.println("Невозможно покинуть пещеру");
            return false;
        }

        System.out.println(cave.hasAlternativeExit() ?
                "Выходим через обнаруженный проход" : "Выходим через главный выход");

        int stolenCount = 0, damagedCount = 0, available = 0;
        for (Specimen s : specimens) {
            if (s.getIsStolen()) stolenCount++;
            if (s.getIsDamaged()) damagedCount++;
            if (s.getIsCollected() && !s.getIsStolen() && !s.getIsDamaged()) available++;
        }

        System.out.println("Доступно для транспортировки: " + available);
        if (stolenCount > 0) System.out.println("Украдено: " + stolenCount);
        if (damagedCount > 0) System.out.println("Повреждено: " + damagedCount);

        return true;
    }

    public void finalizeExpedition(boolean success) {
        System.out.println(success ? "Экспедиция успешно завершена" : "");
        System.out.println("\nИТОГОВЫЙ ОТЧЕТ:");

        int availableCount = 0, stolenCount = 0, damagedCount = 0;
        for (Specimen s : specimens) {
            if (s.getIsCollected() && !s.getIsStolen() && !s.getIsDamaged()) availableCount++;
            if (s.getIsStolen()) stolenCount++;
            if (s.getIsDamaged()) damagedCount++;
        }

        System.out.println("Доступно для транспортировки: " + availableCount);
        if (stolenCount > 0) System.out.println("Украдено предателем: " + stolenCount);
        if (damagedCount > 0) System.out.println("Повреждено собаками: " + damagedCount);

        System.out.println("\nАмбиции команды:");
        for (Researcher r : researchers) {
            String status = "";
            if (r.getIsTraitor()) status = " (ПРЕДАТЕЛЬ)";
            System.out.println("  " + r.getName() + ": " + r.getAmbition() + "%" + status);
        }

        if (success && availableCount > 0) {
            System.out.println("\nПеренаправленно " + availableCount + " особей на базу");
        }
    }
}