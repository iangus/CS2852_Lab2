import edu.msoe.se1010.winPlotter.WinPlotter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
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
    private List<Dot> dotList;
    private List<Dot> resultList = new ArrayList<>();


    public Shape(File file){
        loadFile(file);
    }

    public void loadFile(File fileToLoad){
        if(fileToLoad != null){
            dotList = new ArrayList<>();
            try {
                Scanner fileScan = new Scanner(fileToLoad);
                Scanner lineScan;
                while(fileScan.hasNextLine()){
                    lineScan = new Scanner(fileScan.nextLine());
                    lineScan.useDelimiter(", |,");

                    Dot toAdd = new Dot(lineScan.nextDouble(), lineScan.nextDouble());
                    dotList.add(toAdd);
                }

                for(int i = 0; i < dotList.size(); i++){
                    if (i == 0) {
                        dotList.get(i).setPrevious(dotList.get(dotList.size() - 1));
                        dotList.get(i).setNext(dotList.get(i + 1));
                    }else if(i == dotList.size() - 1){
                        dotList.get(i).setPrevious(dotList.get(i - 1));
                        dotList.get(i).setNext(dotList.get(0));
                    }else {
                        dotList.get(i).setPrevious(dotList.get(i - 1));
                        dotList.get(i).setNext(dotList.get(i + 1));
                    }
                }

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File could not be found.", "File not found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public long getDesiredDots(List<Dot> original, List<Dot> result, int numDesired){
        long startNano = System.nanoTime();
        if(numDesired < 3){
            throw new IllegalArgumentException("Cannot reduce number of dots to less than 3. Desired: " + numDesired);
        }else{
            result.clear();
            result.addAll(original);


            while (result.size() > numDesired) {
                double lowestCrit = 3.0;
                int lowIndex = -1;
                for(int i = 0; i<result.size(); i++){
                    result.get(i).calculateCritVal();
                    if(result.get(i).critVal < lowestCrit){
                        lowestCrit = result.get(i).critVal;
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


            resultList = result;
        }
        long endNano = System.nanoTime();
        return endNano - startNano;
    }

    public long getDesiredDots2(List<Dot> original, Collection<Dot> result, int numDesired){
        long startNano = System.nanoTime();

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
                if(current.critVal < lowCrit){
                    lowCrit = current.critVal;
                    before = current.getPrevious();
                    after = current.getNext();
                    toRemove = current;
                }
            }
            result.remove(toRemove);
            before.setNext(after);
            after.setNext(before);
        }
        long endNano = System.nanoTime();
        return endNano - startNano;
    }

    public int getOriginalSize(){
        return dotList.size();
    }

    public int getReducedSize(){
        return resultList.size();
    }

    public List<Dot> getDotList(){
        return dotList;
    }


    public List<Dot> getResultList(){
        return resultList;
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
