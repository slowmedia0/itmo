public class Main {
    public static void main(String[] args) {
        Expedition expedition = new Expedition();

        expedition.discoverAndCollect();
        expedition.handleBetrayal();

        boolean exitSuccess = false;
        try {
            expedition.prepareForExit();
            exitSuccess = expedition.attemptExit();
        } catch (ExpeditionException e) {
            System.out.println("\n" + e.getMessage());
        }
        expedition.finalizeExpedition(exitSuccess);
    }
}