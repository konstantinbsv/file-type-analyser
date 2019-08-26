package analyzer;

public class RabinKarpStrategy implements IAlgorithmStrategy{
    // constants for polynomial hashing
    private static final int A = 53;
    private static final int M = 1_000_000_000 + 9;


    @Override
    public boolean search(byte[] fileArray, byte[] patternArray) {
        long patternHash = 0;
        long currentSubstringHash = 0;
        long pow = 1; // A^0
        boolean patternFound;

        // find hash of pattern and first substring from right, i.e., suffix
        for (int i = 0; i < patternArray.length; i++) {
            patternHash  += patternArray[i] * pow;
            patternHash %= M;

            currentSubstringHash += fileArray[fileArray.length - patternArray.length + 1] * pow;
            currentSubstringHash &= M;

            // calculate next power
            if (i != patternArray.length - 1) {
                pow = pow * A % M;
            }
        }

        // search file for pattern from end to start
        for (int i = fileArray.length; i >= patternArray.length; i--) {
            if (patternHash == currentSubstringHash) {
                patternFound = true;

                // perform symbol-by-symbol comparison of substring and pattern
                for (int j = 0; j < patternArray.length; j++) {
                    if (fileArray[j] != patternArray[j]) {
                        patternFound = false;
                        break;
                    }
                }

                if (patternFound) {
                    return true;
                }
            }

            // substring not a match, calculate next substring by rolling hash
            if (i > patternArray.length) {
                currentSubstringHash = (currentSubstringHash - fileArray[i-1] * pow % M + M) * A % M;
                currentSubstringHash = (currentSubstringHash + fileArray[i - patternArray.length - 1]) % M;
            }
        }

        return false;
    }
}
