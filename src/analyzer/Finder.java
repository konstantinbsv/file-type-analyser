package analyzer;

class Finder {
    private IAlgorithmStrategy algorithmStrategy;

    Finder(IAlgorithmStrategy algorithmStrategy) {
        this.algorithmStrategy = algorithmStrategy;
    }

    Finder(String selectedAlgorithm) {
        switch (selectedAlgorithm) {
            case "--naive":
                algorithmStrategy = new NaiveStrategy();
                break;
            case "--KMP":
                algorithmStrategy = new KnuthMorrisPrattStrategy();
                break;
            case "--RK":
                algorithmStrategy = new RabinKarpStrategy();
                break;
            default:
                throw new IllegalArgumentException("Invalid algorithm selected.");
        }
    }

    boolean containsPattern(byte[] file, byte[] pattern) {
        return algorithmStrategy.search(file, pattern);
    }

    void setStrategy(IAlgorithmStrategy algorithmStrategy) {
        this.algorithmStrategy = algorithmStrategy;
    }
}
