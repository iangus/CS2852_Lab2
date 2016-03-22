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
        double d12 = calcDistance(this.getX_comp() * 100, previous.getX_comp() * 100, this.getY_comp() * 100, previous.getY_comp() * 100);
        double d23 = calcDistance(this.getX_comp() * 100, next.getX_comp() * 100, this.getY_comp() * 100, next.getY_comp() * 100);
        double d13 = calcDistance(previous.getX_comp() * 100, next.getX_comp() * 100, previous.getY_comp() * 100, next.getY_comp() * 100);

        critVal = d12 + d23 - d13;
    }

    private static double calcDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
