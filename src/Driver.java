/**
 * CS2852 - 041
 * Spring 2016
 * Lab 2
 * Name: Ian Guswiler
 * Created: 3/15/2016
 */

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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Main functioning for program.
 * Includes gui and WinPlotter initialization
 *
 * @author Ian Guswiler
 * @version 3/22/2016
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
    private static final JFileChooser fc = new JFileChooser();
    private File readFile;
    private String fileName;
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
    }

    /**
     * Sets up the plotter to be drawn to
     * @param plotter plotter to be set up
     */
    private void initPlotter(WinPlotter plotter){
        plotter.setWindowTitle(fileName + " Current Dots:" + shape.getReducedSize());
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
            selectFile();
            shape = new Shape(readFile);
            textPoints.setText("" + shape.getOriginalSize());
            ogPoints.setText("Points in original file: " + shape.getOriginalSize());
        });

        dotsButton = new JButton("Dots!");
        dotsButton.addActionListener(e -> {
            try {
                shape.getDesiredDots(Integer.parseInt(textPoints.getText()));
                plotter = new WinPlotter();
                plotter.erase();
                initPlotter(plotter);
                shape.drawDots(plotter);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, textPoints.getText() + " is not valid. Please enter a positive integer.", "Number format exception",JOptionPane.ERROR_MESSAGE);
            }catch (IllegalArgumentException e2){
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Illegal Argument", JOptionPane.ERROR_MESSAGE);
            }
        });

        linesButton = new JButton("Lines!");
        linesButton.addActionListener(e -> {
            try {
                shape.getDesiredDots(Integer.parseInt(textPoints.getText()));
                plotter = new WinPlotter();
                plotter.erase();
                initPlotter(plotter);
                shape.drawLines(plotter);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, textPoints.getText() + " is not valid. Please enter a positive integer.", "Number format exception",JOptionPane.ERROR_MESSAGE);
            }catch (IllegalArgumentException e2){
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Illegal Argument", JOptionPane.ERROR_MESSAGE);
            }
        });

        bothButton = new JButton("Both!");
        bothButton.addActionListener(e -> {
            try {
                shape.getDesiredDots(Integer.parseInt(textPoints.getText()));
                plotter = new WinPlotter();
                plotter.erase();
                initPlotter(plotter);
                shape.drawDots(plotter);
                shape.drawLines(plotter);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, textPoints.getText() + " is not valid. Please enter a positive integer.", "Number format exception",JOptionPane.ERROR_MESSAGE);
            }catch (IllegalArgumentException e2){
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Illegal Argument", JOptionPane.ERROR_MESSAGE);
            }
        });

        timeButton = new JButton("")


        ogPoints = new JLabel("Points in original file: ");
        numPoints = new JLabel("Number of dots: ");

        textPoints = new JTextField(5);
    }

    /**
     * Utilizes the JFileChooser to select a file
     */
    private void selectFile(){
        int returnVal = fc.showOpenDialog(fileButton);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            readFile = fc.getSelectedFile();
            fileName = readFile.getName();
        }
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
