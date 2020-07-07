public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y){
        int diff = java.lang.Math.abs(x - y);
        return diff == 1 || diff == 31 || diff == 33;
    }
}
