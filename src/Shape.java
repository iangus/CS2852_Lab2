/*
 * CS2852 - 041
 * Spring 2016
 * Lab 3
 * Name: Ian Guswiler
 * Created: 3/22/2016
 */

import edu.msoe.se1010.winPlotter.WinPlotter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Processes the file and dots that are loaded into the program
 *
 * @author Ian Guswiler
 * @version 4/4/2016
 */
public class Shape {
    private List<Dot> dotList;


    /**
     * Constructs a Shape object from a given file
     * @param file file to be loaded into a shape
     */
    public Shape(File file){
        loadFile(file);
    }


    /**
     * Uses a for index to remove unnecessary dots until the desired amount is achieved
     * @param original original list of dots to be manipulated
     * @param result resulting list of dots after shortening
     * @param numDesired number of desired dots
     * @return time in nanoseconds taken to complete the action
     */
    public long getDesiredDots(List<Dot> original, List<Dot> result, int numDesired){
        setSurroundingDots(original);
        long startNano = System.nanoTime();
        if(numDesired < 3){
            throw new IllegalArgumentException("Cannot reduce number of dots to less than 3. Desired: " + numDesired);
        }else if(result == null){
            throw new IllegalArgumentException("The specified result List is null.");
        }else if(original == null || original.size() < 3){
            throw new IllegalArgumentException("The specified original List is null or contains less than three dots.");
        }
        else{
            result.clear();
            result.addAll(original);


            while (result.size() > numDesired) {
                double lowestCrit = Double.MAX_VALUE;
                int lowIndex = -1;
                for(int i = 0; i<result.size(); i++){
                    result.get(i).calculateCritVal();
                    if(result.get(i).getCritVal() < lowestCrit){
                        lowestCrit = result.get(i).getCritVal();
                        lowIndex = i;
                    }
                }

                result.remove(lowIndex);
                if(lowIndex == result.size()){
                    result.get(0).setPrevious(result.get(result.size() - 1));
                    result.get(result.size() - 1).setNext(result.get(0));
                }else if(lowIndex == 0){
                    result.get(0).setPrevious(result.get(result.size() - 1));
                    result.get(result.size() - 1).setNext(result.get(lowIndex));
                }else {
                    result.get(lowIndex - 1).setNext(result.get(lowIndex));
                    result.get(lowIndex).setPrevious(result.get(lowIndex - 1));
                }
            }
        }
        long endNano = System.nanoTime();
        return endNano - startNano;
    }

    /**
     * Uses a list iterator to remove unnecessary dots until the desired amount is achieved
     * @param original original list of dots to be manipulated
     * @param result resulting list of dots after shortening
     * @param numDesired number of desired dots
     * @return time in nanoseconds taken to complete the action
     */
    public long getDesiredDots2(List<Dot> original, Collection<Dot> result, int numDesired){
        setSurroundingDots(original);
        long startNano = System.nanoTime();

        if (numDesired < 3) {
            throw new IllegalArgumentException("Cannot reduce number of dots to less than 3. Desired: " + numDesired);
        } else {
            result.clear();
            result.addAll(original);

            while(result.size() > numDesired){
                Iterator dotIterator = result.iterator();
                double lowCrit = 3.0;
                Dot toRemove = null;
                Dot before = null;
                Dot after = null;
                while(dotIterator.hasNext()){
                    Dot current = (Dot) dotIterator.next();
                    current.calculateCritVal();
                    if(current.getCritVal() < lowCrit){
                        lowCrit = current.getCritVal();
                        before = current.getPrevious();
                        after = current.getNext();
                        toRemove = current;
                    }
                }
                result.remove(toRemove);
                before.setNext(after);
                after.setPrevious(before);
            }
        }
        long endNano = System.nanoTime();
        return endNano - startNano;
    }

    /**
     * Gets the original dot list
     * @return original dot list
     */
    public List<Dot> getDotList(){
        return dotList;
    }

    /**
     * draws the dots to a WinPlotter
     * @param plotter WinPlotter to be drawn to
     * @param dots Dots to be drawn
     */
    public void drawDots(WinPlotter plotter, List<Dot> dots){
        plotter.setPenColor(0,0,0);
        for(Dot dot : dots){
            plotter.drawPoint(dot.getX_comp(),dot.getY_comp());
        }
    }

    /**
     * Draws line representation of the shape using the dots as endpoints
     * @param plotter WinPlotter to be drawn to
     * @param dots Dots to be used as points
     */
    public void drawLines(WinPlotter plotter, List<Dot> dots){
        plotter.setPenColor(0,0,0);
        for(Dot dot : dots){
            Dot next = dot.getNext();
            plotter.moveTo(dot.getX_comp(), dot.getY_comp());
            plotter.drawTo(next.getX_comp(),next.getY_comp());
        }
    }

    /**
     * Loads dots from a specified file
     * @param fileToLoad file to be loaded from
     */
    public void loadFile(File fileToLoad){
        if(fileToLoad != null){
            dotList = new ArrayList<>();
            try (Scanner fileScan = new Scanner(fileToLoad)) {
                Scanner lineScan;
                while(fileScan.hasNextLine()){
                    lineScan = new Scanner(fileScan.nextLine());
                    lineScan.useDelimiter(", |,");

                    Dot toAdd = new Dot(lineScan.nextDouble(), lineScan.nextDouble());
                    dotList.add(toAdd);
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File could not be found.", "File not found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * Sets the previous and next attributes for each dot in the specified list
     * @param dots list of dots to be set
     */
    private void setSurroundingDots(List<Dot> dots){
        for(int i = 0; i < dots.size(); i++){
            if (i == 0) {
                dots.get(i).setPrevious(dots.get(dots.size() - 1));
                dots.get(i).setNext(dots.get(i + 1));
            }else if(i == dots.size() - 1){
                dots.get(i).setPrevious(dots.get(i - 1));
                dots.get(i).setNext(dots.get(0));
            }else {
                dots.get(i).setPrevious(dots.get(i - 1));
                dots.get(i).setNext(dots.get(i + 1));
            }
        }
    }
}
