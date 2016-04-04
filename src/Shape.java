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
                for(int i = 0; i < dotsList.size(); i++){
                    if (i == 0) {
                        dotsList.get(i).setPrevious(dotsList.get(dotsList.size() - 1));
                        dotsList.get(i).setNext(dotsList.get(i + 1));
                    }else if(i == dotsList.size() - 1){
                        dotsList.get(i).setPrevious(dotsList.get(i - 1));
                        dotsList.get(i).setNext(dotsList.get(0));
                    }else {
                        dotsList.get(i).setPrevious(dotsList.get(i - 1));
                        dotsList.get(i).setNext(dotsList.get(i + 1));
                    }
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File could not be found.", "File not found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public float getDesiredDots(int numDesired){
        float startNano = System.nanoTime();
        if(numDesired < 3){
            throw new IllegalArgumentException("Cannot reduce number of dots to less than 3. Desired: " + numDesired);
        }else{
            List<Dot> lessDots = new ArrayList<>();
            lessDots.addAll(dotsList);


            while (lessDots.size() > numDesired) {
                double lowestCrit = 3.0;
                int lowIndex = -1;
                for(int i = 0; i<lessDots.size(); i++){
                    lessDots.get(i).calculateCritVal();
                    if(lessDots.get(i).critVal < lowestCrit){
                        lowestCrit = lessDots.get(i).critVal;
                        lowIndex = i;
                    }
                }

                lessDots.remove(lowIndex);
                if(lowIndex == lessDots.size()){
                    lessDots.get(0).setPrevious(lessDots.get(lessDots.size() - 1));
                    lessDots.get(lessDots.size() - 1).setNext(lessDots.get(0));
                }else if(lowIndex == 0){
                    lessDots.get(0).setPrevious(lessDots.get(lessDots.size() - 1));
                    lessDots.get(lessDots.size() - 1).setNext(lessDots.get(lowIndex));
                }else {
                    lessDots.get(lowIndex - 1).setNext(lessDots.get(lowIndex));
                    lessDots.get(lowIndex).setPrevious(lessDots.get(lowIndex - 1));
                }
            }


            resultList = lessDots;
        }
        float endNano = System.nanoTime();
        return endNano - startNano;
    }

    public float getDesiredDots2(List<Dot> original, Collection<Dot> result, int numDesired){
        float startNano = System.nanoTime();

        result.addAll(original);

        while(result.size() > numDesired){
            Iterator dotIterator = result.iterator();
            double lowCrit = 3.0;
            double lowIndex = -1;


        }
        float endNano = System.nanoTime();
        return endNano - startNano;
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
