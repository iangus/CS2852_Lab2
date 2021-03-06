/**
 * CS2825 - 041
 * Spring 2016
 * Lab 2
 * Name: Ian Guswiler
 * Created: 3/15/2016
 */

/**
 * Represents a single dot from a .pnt file
 *
 * @author Ian Gusiwler
 * @version 3/22/2016
 */
public class Dot {
    private double x_comp;
    private double y_comp;
    public double critVal;

    /**
     * Constructs a dot with specified x and y components
     * @param x_comp Dot's x location
     * @param y_comp Dot's y location
     */
    public Dot(double x_comp, double y_comp){
        this.x_comp = x_comp;
        this.y_comp = y_comp;
    }

    /**
     * Returns the x component for the dot
     * @return x component
     */
    public double getX_comp(){
        return x_comp;
    }

    /**
     * Returns the y component for the dot
     * @return y component
     */
    public double getY_comp(){
        return y_comp;
    }

    /**
     * Calculates the critical value for the dot based off of the dots around it
     * @param previous the dot preceding the current dot in the image
     * @param next the dot after the current dot in the image
     */
    public void calculateCritVal(Dot previous, Dot next){
        double d12 = calcDistance(this.getX_comp(), previous.getX_comp(), this.getY_comp(), previous.getY_comp());
        double d23 = calcDistance(this.getX_comp(), next.getX_comp(), this.getY_comp(), next.getY_comp());
        double d13 = calcDistance(previous.getX_comp(), next.getX_comp(), previous.getY_comp(), next.getY_comp());

        critVal = d12 + d23 - d13;
    }

    /**
     * Calculates the distance between two points returning the calculated value
     * @param x1 x location of the first point
     * @param x2 x location of the second point
     * @param y1 y location of the first point
     * @param y2 y locaiton of the second point
     * @return calculated distance value
     */
    private static double calcDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
