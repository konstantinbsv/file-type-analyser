package analyzer;

public class KnuthMorrisPrattStrategy implements IAlgorithmStrategy {

    @Override
    public boolean search(byte[] fileArray, byte[] patternArray) {
        int[] prefixFunc = prefixFunction(patternArray);
        int j = 0;

        for (int i = 0; i < fileArray.length; i++) {
            while (j > 0 && fileArray[i] != patternArray[j]) {
                j = prefixFunc[j - 1];
            }

            if (fileArray[i] == patternArray[j]) {
                j += 1;
            }

            if (j == patternArray.length) {
                return true;
            }
        }

        return false;
    }

    private int[] prefixFunction(byte[] byteArray) {

        int[] prefixFunc = new int[byteArray.length];

        for (int i = 1; i < byteArray.length; i++) {
            int j = prefixFunc[i - 1];

            while (j > 0 && byteArray[i] != byteArray[j]) {
                j = prefixFunc[j - 1];
            }

            if (byteArray[i] == byteArray[j]) {
                j += 1;
            }

            prefixFunc[i] = j;
        }

        return prefixFunc;
    }
}
