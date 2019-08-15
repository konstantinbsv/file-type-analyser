package analyzer;

public class NaiveStrategy implements IAlgorithmStrategy {

    @Override
    public boolean search(byte[] fileArray, byte[] patternArray) {
        boolean found = false;

        int fileSize = fileArray.length;
        int patternSize = patternArray.length;

        // declare and initialize counters
        int i = 0;
        int j = 0;

        while(i < fileSize && j < patternSize) {
            if (fileArray[i] == patternArray[j]) {
                i++;
                j++;

                // if search pattern array completely traversed
                if (j == patternSize) {
                    found = true;
                    break;
                }
            }
            // if bytes do not match, start search with one byte forward offset in file
            else {
                i++;
                j = 0;
            }
        }

        return found;
    }
}
