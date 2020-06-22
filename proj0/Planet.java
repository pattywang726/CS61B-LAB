import java.lang.Math;

public class Planet {

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet (double xP, double yP, double xV,
                  double yV,double m, String img){
                    xxPos = xP;
                    yyPos = yP;
                    xxVel = xV;
                    yyVel = yV;
                    mass = m;
                    imgFileName = img;
                  }
/* the second constructor should take in a Planet object(e.g. Jupiter), and initizlie an identical Planet object. */
    public Planet (Planet p){
                    p.xxPos = xxPos;
                    p.yyPos = yyPos;
                    p.xxVel = xxVel;
                    p.yyVel = yyVel;
                    p.mass = mass;
                    p.imgFileName = imgFileName;
                  }
/* calculates the distance between two Planets */
    public double calcDistance(Planet p2){
      double dx;
      double dy;
      double dist;
      dx = p2.xxPos - this.xxPos;
      dy = p2.yyPos - this.yyPos;
      dist = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
      return dist;
    }

    public double calcForceExertedBy(Planet p2){
      double F;
      double G = 6.67 * Math.pow(10,-11);
      F = (G * p2.mass * this.mass) / Math.pow(calcDistance(p2),2);
      return F;
    }

    public double calcForceExertedByX(Planet p2){
      double Fx;
      Fx = calcForceExertedBy(p2) * (p2.xxPos - this.xxPos) / calcDistance(p2);
      return Fx;
    }

    public double calcForceExertedByY(Planet p2){
      double Fy;
      Fy = calcForceExertedBy(p2) * (p2.yyPos - this.yyPos) / calcDistance(p2);
      return Fy;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets){
      double FnetX = 0;
      for (int i=0; i<allPlanets.length; i+=1){
        if (this.equals(allPlanets[i])){
          continue;
        }
        FnetX = FnetX + calcForceExertedByX(allPlanets[i]);
      }
      return FnetX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
      double FnetY = 0;
      for (int i=0; i<allPlanets.length; i+=1){
        if (this.equals(allPlanets[i])){
          continue;
        }
        FnetY = FnetY + calcForceExertedByY(allPlanets[i]);
      }
      return FnetY;
    }

    public void update(double dt, double fX, double fY){
      double aX;
      double aY;
      double vX;
      double vY;
      this.xxVel = this.xxVel + dt * (fX / this.mass);
      this.yyVel = this.yyVel + dt * (fY / this.mass);
      this.xxPos = this.xxPos + dt * this.xxVel;
      this.yyPos = this.yyPos + dt * this.yyVel;
    }

    // public String imagePlanet = this.imgFileName;  /*??? it is no input, cannot use this.imgFileName, this.xxPos or this.yyPos*/
    private void draw(){
                                   /*imgFileName is obtianed from Me, so method draw should be non-static;*/
      StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName); /*imgFileName has been set at the beginning, "public String imagePlanet = imgFileName;" doesn't work!!!*/
    }

}
