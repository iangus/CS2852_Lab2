/*
 * CS2852 - 041
 * Spring 2016
 * Lab 3
 * Name: Ian Guswiler
 * Created: 3/15/2016
 */

import edu.msoe.se1010.winPlotter.WinPlotter;
import java.awt.FlowLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Main functioning for program.
 * Includes gui and WinPlotter initialization
 *
 * @author Ian Guswiler
 * @version 4/4/2016
 */
public class Driver extends JFrame{
    private JButton fileButton;
    private JButton dotsButton;
    private JButton linesButton;
    private JButton bothButton;
    private JButton timeButton;
    private JLabel ogPoints;
    private JLabel numPoints;
    private JTextField textPoints;
    private Shape shape;
    private WinPlotter plotter;

    /**
     * Constructs a default driver object with predetermined attributes
     */
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
        add(timeButton);
    }

    /**
     * Sets up the plotter to be drawn to
     * @param plotter plotter to be set up
     */
    private void initPlotter(WinPlotter plotter){
        plotter.setWindowSize(800,800);
        plotter.setPlotBoundaries(0.0,0.0,1.0,1.0);
        plotter.setBackgroundColor(255, 255, 255);
    }


    /**
     * Creates the gui components that are to be added to the frame. Adds action listeners to the buttons.
     */
    private void createComponents(){
        fileButton = new JButton("Select File");
        fileButton.addActionListener(e -> {
            shape = new Shape(selectFile());
            textPoints.setText("" + shape.getDotList().size());
            ogPoints.setText("Points in original file: " + shape.getDotList().size());
        });

        dotsButton = new JButton("Dots!");
        dotsButton.addActionListener(e -> {
            try {
                List<Dot> result = new ArrayList<>();
                shape.getDesiredDots(shape.getDotList(), result, Integer.parseInt(textPoints.getText()));
                plotter = new WinPlotter();
                plotter.erase();
                initPlotter(plotter);
                shape.drawDots(plotter, result);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, textPoints.getText() + " is not valid. Please enter a positive integer.",
                        "Number format exception",JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e2){
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Illegal Argument", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e3){
                JOptionPane.showMessageDialog(null, "A shape has not been loaded.", "File not loaded",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        linesButton = new JButton("Lines!");
        linesButton.addActionListener(e -> {
            try {
                List<Dot> result = new ArrayList<>();
                shape.getDesiredDots2(shape.getDotList(), result, Integer.parseInt(textPoints.getText()));
                plotter = new WinPlotter();
                plotter.erase();
                initPlotter(plotter);
                shape.drawLines(plotter, result);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, textPoints.getText() + " is not valid. Please enter a positive integer.",
                        "Number format exception",JOptionPane.ERROR_MESSAGE);
            }catch (IllegalArgumentException e2){
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Illegal Argument", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e3){
                JOptionPane.showMessageDialog(null, "A shape has not been loaded.", "File not loaded",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        bothButton = new JButton("Both!");
        bothButton.addActionListener(e -> {
            try {
                List<Dot> result = new ArrayList<>();
                shape.getDesiredDots(shape.getDotList(), result, Integer.parseInt(textPoints.getText()));
                plotter = new WinPlotter();
                plotter.erase();
                initPlotter(plotter);
                shape.drawDots(plotter, result);
                shape.drawLines(plotter, result);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, textPoints.getText() + " is not valid. Please enter a positive integer.",
                        "Number format exception",JOptionPane.ERROR_MESSAGE);
            }catch (IllegalArgumentException e2){
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Illegal Argument", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e3){
                JOptionPane.showMessageDialog(null, "A shape has not been loaded.", "File not loaded",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        timeButton = new JButton("Time!");
        timeButton.addActionListener(e -> {
            try {
                long indexedArray = shape.getDesiredDots(shape.getDotList(), new ArrayList<>(), Integer.parseInt(textPoints.getText()));
                long indexedLinked = shape.getDesiredDots(shape.getDotList(), new LinkedList<>(), Integer.parseInt(textPoints.getText()));
                long iteratedArray = shape.getDesiredDots2(shape.getDotList(), new ArrayList<>(), Integer.parseInt(textPoints.getText()));
                long iteratedLinked = shape.getDesiredDots2(shape.getDotList(), new LinkedList<>(), Integer.parseInt(textPoints.getText()));


                JOptionPane.showMessageDialog(null, "Indexed ArrayList: " + formatTime(indexedArray) + "\n" +
                        "Indexed LinkedList: " + formatTime(indexedLinked) + "\n" +
                        "Iterated ArrayList: " + formatTime(iteratedArray) + "\n" +
                        "Iterated LinkedList: " + formatTime(iteratedLinked));
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, textPoints.getText() + " is not valid. Please enter a positive integer.",
                        "Number format exception",JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e2) {
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Illegal Argument", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e3){
                JOptionPane.showMessageDialog(null, "A shape has not been loaded.", "File not loaded",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        ogPoints = new JLabel("Points in original file: ");
        numPoints = new JLabel("Number of dots: ");

        textPoints = new JTextField(5);
    }

    /**
     * Utilizes the JFileChooser to select a file
     */
    private File selectFile(){
        JFileChooser fc = new JFileChooser();
        File readFile = null;
        int returnVal = fc.showOpenDialog(fileButton);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            readFile = fc.getSelectedFile();
        }
        return readFile;
    }

    /**
     * Formats a given long of nanoseconds into the correct HH:mm:ss.SSSS format
     * @param nanos a long representing an elapsed time in nanoseconds
     * @return formatted string representation of the given time
     */
    private String formatTime(long nanos){
        SimpleDateFormat sdf = new SimpleDateFormat("00:mm:ss.SSSS");
        Date date = new Date(TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS));
        return sdf.format(date);
    }


    /**
     * Creates a new Driver object and sets it to visible
     * @param args Ignored
     */
    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.setVisible(true);
    }
}
