import edu.msoe.se1010.winPlotter.WinPlotter;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * CS2852 - 041
 * Spring 2016
 * Lab 2
 * Name: Ian Guswiler
 * Created: 3/15/2016
 */
public class Driver extends JFrame{
    private JButton fileButton;
    private JButton dotsButton;
    private JButton linesButton;
    private JButton bothButton;
    private JLabel ogPoints;
    private JLabel numPoints;
    private JTextField textPoints;
    private JFileChooser fc = new JFileChooser();
    private File readFile;
    private String fileName;
    private List<Dot> dotsList = new ArrayList<>();
    private List<Dot> resultList = new ArrayList<>();
    private WinPlotter plotter;

    public Driver(){
        setSize(200,185);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        createComponents();

        add(fileButton);
        add(ogPoints);
        add(numPoints);
        add(textPoints);
        add(dotsButton);
        add(linesButton);
        add(bothButton);
    }

    private void initPlotter(WinPlotter plotter){
        plotter.setWindowTitle(fileName + " Current Dots:" + resultList.size());
        plotter.setWindowSize(800,800);
        plotter.setPlotBoundaries(0.0,0.0,1.0,1.0);
        plotter.setBackgroundColor(255,255,255);
    }

    private void drawDots(WinPlotter plotter){
        plotter.setPenColor(0,0,0);
        for(Dot dot : resultList){
            plotter.drawPoint(dot.getX_comp(),dot.getY_comp());
        }
    }

    private void drawLines(WinPlotter plotter){
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

    private void createComponents(){
        fileButton = new JButton("Select File");
        fileButton.addActionListener(e -> {
            selectFile();
            loadFile();
            ogPoints.setText("Points in original file: " + dotsList.size());
        });

        dotsButton = new JButton("Dots!");
        dotsButton.addActionListener(e -> {
            getDesiredDots(Integer.parseInt(textPoints.getText()));
            plotter = new WinPlotter();
            plotter.erase();
            initPlotter(plotter);
            drawDots(plotter);
        });

        linesButton = new JButton("Lines!");
        linesButton.addActionListener(e -> {
            getDesiredDots(Integer.parseInt(textPoints.getText()));
            plotter = new WinPlotter();
            plotter.erase();
            initPlotter(plotter);
            drawLines(plotter);
        });

        bothButton = new JButton("Both!");
        bothButton.addActionListener(e -> {
            getDesiredDots(Integer.parseInt(textPoints.getText()));
            plotter = new WinPlotter();
            plotter.erase();
            initPlotter(plotter);
            drawDots(plotter);
            drawLines(plotter);
        });


        ogPoints = new JLabel("Points in original file: ");
        numPoints = new JLabel("Number of dots: ");

        textPoints = new JTextField(5);
    }

    private void selectFile(){
        int returnVal = fc.showOpenDialog(fileButton);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            readFile = fc.getSelectedFile();
            fileName = readFile.getName();
        }
    }

    private void loadFile(){
        if(readFile != null){
            dotsList = new ArrayList<>();
            try {
                Scanner fileScan = new Scanner(readFile);
                Scanner lineScan;
                while(fileScan.hasNextLine()){
                    lineScan = new Scanner(fileScan.nextLine());
                    lineScan.useDelimiter(", |,");

                    dotsList.add(new Dot(lineScan.nextDouble(), lineScan.nextDouble()));
                }
                textPoints.setText("" + dotsList.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDesiredDots(int numDesired){
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


    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.setVisible(true);
    }
}
