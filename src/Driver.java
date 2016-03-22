import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
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
    JButton fileButton;
    JButton dotsButton;
    JButton linesButton;
    JButton bothButton;
    JLabel ogPoints;
    JLabel numPoints;
    JTextField textPoints;
    JFileChooser fc = new JFileChooser();
    File readFile;
    List<Dot> dotsList = new ArrayList<>();

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

    private void createComponents(){
        fileButton = new JButton("Select File");
        fileButton.addActionListener(e -> {
            selectFile();
            loadFile();
            ogPoints.setText("Points in original file: " + dotsList.size());
        });

        dotsButton = new JButton("Dots!");
        dotsButton.addActionListener(e -> getDesiredDots(Integer.parseInt(textPoints.getText())));

        linesButton = new JButton("Lines!");
        linesButton.addActionListener(e -> getDesiredDots(Integer.parseInt(textPoints.getText())));

        bothButton = new JButton("Both!");
        bothButton.addActionListener(e -> getDesiredDots(Integer.parseInt(textPoints.getText())));


        ogPoints = new JLabel("Points in original file: ");
        numPoints = new JLabel("Number of dots: ");

        textPoints = new JTextField(5);
    }

    private void selectFile(){
        int returnVal = fc.showOpenDialog(fileButton);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            readFile = fc.getSelectedFile();
        }
    }

    private void loadFile(){
        if(readFile != null){
            try {
                Scanner fileScan = new Scanner(readFile);
                while(fileScan.hasNext()){
                    dotsList.add(new Dot();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDesiredDots(int numDesired){
        List<Dot> lessDots = dotsList;
        double lowestCrit = 3.0;
        int lowIndex = -1;


        while (lessDots.size() > numDesired) {
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


        dotsList = lessDots;
    }


    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.setVisible(true);
    }
}
