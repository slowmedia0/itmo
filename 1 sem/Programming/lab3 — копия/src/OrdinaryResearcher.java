class OrdinaryResearcher extends Researcher {
    private final String[] funReactToSpecimen = {"Интересный образец...","Невероятно!","Получилось!"};
    private final String[] sadReactToSpecimen = {"Не получилось...","В другой раз","Это печально"};
    public OrdinaryResearcher(String name) {
        super(name);
    }
    public String reactToSpecimen(Specimen s) {
        return s.getIsCollected() ?  funReactToSpecimen[rand.nextInt(funReactToSpecimen.length)]: sadReactToSpecimen[rand.nextInt(sadReactToSpecimen.length)];
    }
    public boolean understandsElderThings() {
        return knowsNecronomicon();
    }
}