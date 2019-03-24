public class OffByN implements CharacterComparator {
    private int number;
    OffByN(int N) {
        number = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == number) {
            return true;
        } else {
            return false;
        }
    }
}
