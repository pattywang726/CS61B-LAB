public class Drawing_Triangle {
	public static void main(String[] args) {
    double x = 1;
    while (x < 6){
      double y = 1;
      while (y < x){
        System.out.print('*');
        y = y + 1;
      }
      System.out.println('*');
      x = x + 1;
    }
	}
}
