class ExpeditionException extends Exception {
    public ExpeditionException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "Экспедиция прервана: " + super.getMessage();
    }
}