public class NBody{
  public static double readRadius(String filename){
    In in = new In(filename);   /*path can be used in the In class method */

    int N = in.readInt();
    double radius = in.readDouble();
    return radius;
  }

  public static Planet[] readPlanets(String filename){
    In in = new In(filename);

    int N = in.readInt();
    double radius = in.readDouble();

    Planet[] allPlanets = new Planet[N];

    for (int j=0; j<N; j+=1){
      double xP = in.readDouble();
      double yP = in.readDouble();
      double xV = in.readDouble();
      double yV = in.readDouble();
      double m = in.readDouble();
      String img = in.readString();
      allPlanets[j] = new Planet(xP,yP,xV,yV,m,img);
    }
    return allPlanets;
  }

  public static String imageToDraw = "./images/starfield.jpg";
  public static void drawing(double Radius, Planet[] allPlanets){
    /** Sets up the universe, it is square, since there is no setXScale and setYScale **/
    StdDraw.setScale(0, Radius); /*default is 0-1.0 */
    StdDraw.clear();
    StdDraw.picture(Radius/2, Radius/2, imageToDraw);

    for (int k=0; k<allPlanets.length; k+=1){
      allPlanets[k].draw();
    }

    StdDraw.show();
    StdDraw.pause(2000);
    }

  public static void main(String[] args){

    if (args.length == 0) {
			System.out.println("Please supply the needed info");
      }
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];

    double Radius = readRadius(filename);
    Planet[] allPlanets = readPlanets(filename);

    drawing(Radius, allPlanets);

    StdDraw.enableDoubleBuffering();

    for(double t=0; t<T; t+=dt){
      double[] xForces = new double[allPlanets.length];
      double[] yForces = new double[allPlanets.length];

      for(int k=0; k<allPlanets.length; k+=1){
        xForces[k] = allPlanets[k].calcNetForceExertedByX(allPlanets);
        yForces[k] = allPlanets[k].calcNetForceExertedByY(allPlanets);
      }
      for(int k=0; k<allPlanets.length; k+=1){
        allPlanets[k].update(dt, xForces[k], yForces[k]);
      }
      StdDraw.clear();
      drawing(Radius, allPlanets);
      StdDraw.show();
      StdDraw.pause(10);
    }

    StdOut.printf("%d\n", allPlanets.length);
    StdOut.printf("%.2e\n", Radius);
    for (int i = 0; i < allPlanets.length; i++) {
    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
                  allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);
    }
  }
}
