package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Represents application's main window frame.
public class TravelPlannerUI extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private ImageIcon travelFile;

    public TravelPlannerUI() {
        super("Travel Planner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JPanel panel = (JPanel) getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //add button and label
        //TODO: how to center button
        panel.add(new JLabel("Welcome to Travel Planner"));
        JButton start = new JButton("Start");
        start.addActionListener(this);
        start.setActionCommand("Start");
        panel.add(start);

        // add image
        loadImages();
        JLabel travelImage = new JLabel(travelFile);
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
    // https://
    // EFFECTS: load images
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + sep
                    + "images" + sep + "travel.png"));
            travelFile = new ImageIcon(image.getScaledInstance(250, 250, Image.SCALE_DEFAULT));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static void main(String[] args) {
        new TravelPlannerUI();
    }
}
