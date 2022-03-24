package ui;

import model.Activity;
import model.ActivityList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//TODO: add reference and documentation

// Method taken from TextInputDemo class in
// https://

// Represents application's add and display activities window frame.
public class ActivityUI extends JPanel implements ActionListener, FocusListener {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private ActivityList travelPlanner;
    private boolean createActivity;
    JPanel addPanel;
    JPanel displayPanel;
    JTextField nameField;
    JTextField locationField;
    JTextField hoursField;
    JTextArea display;
    private static final String JSON_STORE = "./data/travelplanner.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ActivityUI() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        addPanel = new JPanel();
        addPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));
        addPanel.add(createEntryFields());
        addPanel.add(createButtons());
        add(addPanel); //left side of panel: create activity
        add(displayPanel()); //right side of panel: display
        // TODO: add a boolean value as a parameter to check if it's a new / load operation
        //  and construct travelPlanner differently
        travelPlanner = new ActivityList(); //construct new travel planner
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECTS: create buttons on the panel
    public JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        JButton addButton = new JButton("Add Activity");
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete Activity");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("delete");
        panel.add(deleteButton);

        JButton clearButton = new JButton("Clear"); //clear text box
        clearButton.addActionListener(this);
        clearButton.setActionCommand("clear");
        panel.add(clearButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        panel.add(saveButton);
        panel.setBorder(BorderFactory.createEmptyBorder());

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        loadButton.setActionCommand("load");
        panel.add(loadButton);

        return panel;
    }

    // EFFECTS: called when the user clicks the button
    public void actionPerformed(ActionEvent e) {
        if ((e.getActionCommand()).equals("clear")) {
            createActivity = false;
            nameField.setText("");
            locationField.setText("");
            hoursField.setText("");
        } else if ((e.getActionCommand()).equals("delete")) {
            createActivity = true;
            Activity toBeDeleted = travelPlanner.findActivity(nameField.getText());
            travelPlanner.deleteActivity(toBeDeleted);
        } else if ((e.getActionCommand()).equals("add")) {
            createActivity = true;
            Activity activity = new Activity(
                    nameField.getText(), locationField.getText(),
                    Integer.valueOf(hoursField.getText()));
            travelPlanner.addActivity(activity);
        } else if ((e.getActionCommand()).equals("save")) {
            createActivity = true;
            saveTravelPlan();
        } else { //((e.getActionCommand()).equals("load"))
            createActivity = true;
            loadTravelPlan();
        }
        updateDisplays();
    }

    // TODO: adjust label position
    //EFFECTS: create entry fields on the panel
    public JComponent createEntryFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));

        String[] labelStrings = {
                "Description: ",
                "Location: ",
                "Hours: "
        };
        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;
        //Create the text field and set it up.
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(WIDTH / 2, 20));
        //nameField.setColumns(20);
        fields[fieldNum++] = nameField;

        locationField = new JTextField();
        locationField.setMaximumSize(new Dimension(WIDTH / 2, 20));
        //locationField.setColumns(20);
        fields[fieldNum++] = locationField;

        hoursField = new JTextField();
        hoursField.setMaximumSize(new Dimension(WIDTH / 2, 20));
        //hoursField.setColumns(20);
        fields[fieldNum++] = hoursField;

        //Associate label/field pairs, add everything, and lay it out.
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i], JLabel.LEADING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);

            //Add listeners to each field.
            JTextField tf = null;
            tf = (JTextField) fields[i];
            tf.addActionListener(this);
            tf.addFocusListener(this);
        }
        return panel;
    }

    public void createField() {

    }

    //EFFECTS: create display activity panel
    public JComponent displayPanel() {
        displayPanel = new JPanel(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        displayPanel.setBorder(BorderFactory.createEmptyBorder());
        displayPanel.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START); //separate left and right
        display = new JTextArea();
        //display.setVerticalAlignment(JLabel.TOP);
        updateDisplays();
        displayPanel.add(display, BorderLayout.CENTER);
        return displayPanel;
    }

    //EFFECTS:
    protected void updateDisplays() {
        display.setText(displayActivity());
    }

    public String displayActivity() {
        if (createActivity) {
            String activityString = "";
            ArrayList<Activity> planner = travelPlanner.getPlanner();
            for (Activity activity : planner) {
                activityString += activity.toString() + "\n";
            }
            return activityString;
        } else {
            return ""; //No activity
        }
    }

    // EFFECTS: saves travel plan from file
    private void saveTravelPlan() {
        try {
            jsonWriter.open();
            jsonWriter.write(travelPlanner);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file");
        }
    }

    // EFFECTS: loads travel plan from file
    private void loadTravelPlan() {
        try {
            travelPlanner = jsonReader.read();
            updateDisplays();
        } catch (IOException e) {
            System.out.println("Unable to read file");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    // EFFECTS: create a frame to show GUI
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Travel Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new ActivityUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
