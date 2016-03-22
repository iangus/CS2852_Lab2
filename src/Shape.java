import edu.msoe.se1010.winPlotter.WinPlotter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * CS2852 - 041
 * Spring 2016
 * Lab
 * Name: Ian Guswiler
 * Created: 3/22/2016
 */
public class Shape {
    private List<Dot> dotsList = new ArrayList<>();
    private List<Dot> resultList = new ArrayList<>();

    public Shape(ArrayList<Dot> dots){
        dotsList = dots;
    }

    public Shape(File file){
        loadFile(file);
    }

    public void loadFile(File fileToLoad){
        if(fileToLoad != null){
            dotsList = new ArrayList<>();
            try {
                Scanner fileScan = new Scanner(fileToLoad);
                Scanner lineScan;
                while(fileScan.hasNextLine()){
                    lineScan = new Scanner(fileScan.nextLine());
                    lineScan.useDelimiter(", |,");

                    dotsList.add(new Dot(lineScan.nextDouble(), lineScan.nextDouble()));
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File could not be found.", "File not found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void getDesiredDots(int numDesired){
        if(numDesired < 3){
            throw new IllegalArgumentException("Cannot reduce number of dots to less than 3. Desired: " + numDesired);
        }else{
            List<Dot> lessDots = new ArrayList<>();
            lessDots.addAll(dotsList);


            while (lessDots.size() > numDesired) {
                double lowestCrit = 3.0;
                int lowIndex = -1;
                for(int i = 1; i<lessDots.size() - 1; i++){
                    Dot previous = lessDots.get(i - 1);
                    Dot next = lessDots.get(i + 1);
                    lessDots.get(i).calculateCritVal(previous, next);
                    if(lessDots.get(i).critVal < lowestCrit){
                        lowestCrit = lessDots.get(i).critVal;
                        lowIndex = i;
                    }
                }

                lessDots.remove(lowIndex);
            }


            resultList = lessDots;
        }
    }

    public int getOriginalSize(){
        return dotsList.size();
    }

    public int getReducedSize(){
        return resultList.size();
    }

    public void drawDots(WinPlotter plotter){
        plotter.setPenColor(0,0,0);
        for(Dot dot : resultList){
            plotter.drawPoint(dot.getX_comp(),dot.getY_comp());
        }
    }

    public void drawLines(WinPlotter plotter){
        plotter.setPenColor(0,0,0);
        for(int i = 0; i < resultList.size(); i++){
            Dot current = resultList.get(i);
            Dot next;
            if(i == resultList.size() - 1){
                next = resultList.get(0);
            }else{
                next = resultList.get(i + 1);
            }
            plotter.moveTo(current.getX_comp(), current.getY_comp());
            plotter.drawTo(next.getX_comp(),next.getY_comp());
        }
    }
}
