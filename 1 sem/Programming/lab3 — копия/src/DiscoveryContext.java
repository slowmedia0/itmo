record DiscoveryContext(
        GeologicalPeriod period,
        double preservationFactor
) {
    public DiscoveryContext {
        preservationFactor = Math.max(0, Math.min(1, preservationFactor));
    }
}