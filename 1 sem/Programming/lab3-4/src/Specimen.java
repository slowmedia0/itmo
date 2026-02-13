import java.util.Random;

class Specimen {
    private static final String[] COLLECTION_MESSAGES = {
            "Аккуратно извлекли из породы", "Подготовили к транспортировке",
            "Упаковали для отправки на базу", "Изолировали от внешней среды"
    };

    private static final String[] FAILURE_MESSAGES = {
            "Требует дополнительной обработки", "Слишком глубоко в породе",
            "Нужны специальные инструменты", "Оставили для дальнейшего изучения"
    };

    private final String name;
    private final DiscoveryContext context;
    private boolean isCollected = false;
    private boolean isStolen = false;
    private boolean isDamaged = false;
    private final Random rand = new Random();

    public Specimen(String name, GeologicalPeriod period) {
        this.name = name;
        this.context = new DiscoveryContext(period, rand.nextDouble());
    }

    public boolean tryToCollect() {
        double successChance = context.preservationFactor();
        if (rand.nextDouble() < successChance) {
            isCollected = true;
            String message = COLLECTION_MESSAGES[rand.nextInt(COLLECTION_MESSAGES.length)];
            System.out.println(message + ": " + name);
            return true;
        } else {
            String message = FAILURE_MESSAGES[rand.nextInt(FAILURE_MESSAGES.length)];
            System.out.println(message + ": " + name);
            return false;
        }
    }

    public boolean damageByDog() {
        if (this.getIsCollected() && !this.getIsDamaged() && !this.getIsStolen()) {
            if (rand.nextDouble() < 0.5) {
                isDamaged = true;
                System.out.println(name + " повреждена собакой!");
            }
        }
        return isDamaged;
    }

    public String getName() { return name; }
    public boolean getIsCollected() { return isCollected; }
    public boolean getIsStolen() { return isStolen; }
    public boolean getIsDamaged() { return isDamaged; }
    public GeologicalPeriod getPeriod() { return context.period(); }
    public void setIsStolen(boolean stolen){ this.isStolen=stolen;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Specimen that = (Specimen) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + (isCollected ? (isStolen ? " [Stolen]" : "") + (isDamaged ? " [Damaged]" : "") : "[NotCollected]");
    }
}