package analyzer;

class Finder {
    private IAlgorithmStrategy algorithmStrategy;

    Finder(IAlgorithmStrategy algorithmStrategy) {
        this.algorithmStrategy = algorithmStrategy;
    }

    boolean containsPattern(byte[] file, byte[] pattern) {
        return algorithmStrategy.search(file, pattern);
    }

    void setStrategy(IAlgorithmStrategy algorithmStrategy) {
        this.algorithmStrategy = algorithmStrategy;
    }
}
