public class HorribleSteve {
    public static void main(String [] args) {
        int m = 0;
        for (int n = 0; m < 500; ++m, ++n) {
            if (!Flik.isSameNumber(m, n)) {
                break; // break exits the for loop!
            }
        }
        System.out.println("i is " + m);
    }
}
