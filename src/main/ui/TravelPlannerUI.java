package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Represents travel planner application's main window frame.
public class TravelPlannerUI extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private ImageIcon travel;

    // EFFECTS: create travel planner UI
    public TravelPlannerUI() {
        super("Travel Planner"); //title
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JPanel panel = (JPanel) getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.getHSBColor(255, 229,204));

        //TODO: how to center button and set border
        JLabel welcome = new JLabel("Welcome to Travel Planner");
        welcome.setFont(new Font("Arial Black", Font.PLAIN, 16));
        panel.add(welcome);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); //gap between component

        JButton start = new JButton("Start");
        start.addActionListener(this);
        start.setActionCommand("Start");
        panel.add(start);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        loadImages();
        JLabel travelImage = new JLabel(travel);
        panel.add(travelImage);

        pack(); // size the frame
        setVisible(true); // make visible
    }

    @Override
    // EFFECTS: called when the user clicks the button
    public void actionPerformed(ActionEvent e) {
        ActivityUI.createAndShowGUI();
    }

    // Method taken from TrafficLightGUI class in
    // https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabSolution.git
    // EFFECTS: load image
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + sep
                    + "images" + sep + "travel.png"));
            travel = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static void main(String[] args) {
        new TravelPlannerUI();
    }
}
