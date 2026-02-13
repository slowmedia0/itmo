import java.util.Random;

class Cave {
    private boolean mainExitBlocked = false;
    private boolean alternativeExitFound = false;
    private final Random rand = new Random();

    public boolean checkForCollapse() {
        if (rand.nextDouble() < 0.5) {
            mainExitBlocked = true;
            return true;
        }
        return false;
    }

    public boolean handleCollapse() {
        if (!mainExitBlocked) return true;

        System.out.println("\nПытаемся решить проблему выхода...");

        if (rand.nextDouble() < 0.5) {
            if (rand.nextBoolean()) {
                System.out.print("Расчищаем завал... ");
                if (rand.nextDouble() < 0.5) {
                    mainExitBlocked = false;
                    System.out.println("УСПЕХ!");
                    return true;
                } else {
                    System.out.println("НЕУДАЧА - завал слишком большой!");
                    return false;
                }
            } else {
                System.out.print("Ищем другой выход... ");
                if (rand.nextDouble() < 0.5) {
                    alternativeExitFound = true;
                    System.out.println("НАШЛИ!");
                    return true;
                } else {
                    System.out.println("НЕТ - других выходов не найдено!");
                    return false;
                }
            }
        }
        return false;
    }

    public boolean canExit() {
        return !mainExitBlocked || alternativeExitFound;
    }

    public boolean hasAlternativeExit() {
        return alternativeExitFound;
    }
}