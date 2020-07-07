public class OffByN implements CharacterComparator {
    public int N;

    public OffByN(int number){
        N = number;
    }

    @Override
    public boolean equalChars(char x, char y){
        int diff = java.lang.Math.abs(x - y);
        return diff == N;
    }
}
