/**
 * CS2825 - 041
 * Spring 2016
 * Lab 2
 * Name: Ian Guswiler
 * Created: 3/15/2016
 */
public class Dot {
    private double x_comp;
    private double y_comp;
    public double critVal;

    public Dot(double x_comp, double y_comp){
        this.x_comp = x_comp;
        this.y_comp = y_comp;
    }

    public double getX_comp(){
        return x_comp;
    }

    public double getY_comp(){
        return y_comp;
    }

    public void calculateCritVal(Dot previous, Dot next){
        double d12 = calcDistance(this.getX_comp(), previous.getX_comp(), this.getY_comp(), previous.getY_comp());
        double d23 = calcDistance(this.getX_comp(), next.getX_comp(), this.getY_comp(), next.getY_comp());
        double d13 = calcDistance(previous.getX_comp(), next.getX_comp(), previous.getY_comp(), next.getY_comp());

        critVal = d12 + d23 - d13;
    }

    private static double calcDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
