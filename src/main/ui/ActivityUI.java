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
public class ActivityUI extends JPanel implements ActionListener, FocusListener { //FocusListener
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private ActivityList travelPlanner;
    private boolean createActivity;
    JPanel addPanel;
    JPanel displayPanel;
    JPanel entryPanel;
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
        travelPlanner = new ActivityList(); //construct new travel planner
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECTS: create buttons on the panel
    public JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add Activity");
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete Activity");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("delete");
        panel.add(deleteButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        panel.add(saveButton);
        panel.setBorder(BorderFactory.createEmptyBorder());

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        loadButton.setActionCommand("load");
        panel.add(loadButton);

        JButton clearButton = new JButton("Clear"); //clear text box
        clearButton.addActionListener(this);
        clearButton.setActionCommand("clear");
        panel.add(clearButton);

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
        entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
        entryPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));

        String[] labelStrings = {"Description: ", "Location: ", "Hours: "};
        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];

        setUp(fields);
//        int fieldNum = 0;
//
//        nameField = new JTextField();
//        nameField.setMaximumSize(new Dimension(WIDTH / 2, 20));
//        fields[fieldNum++] = nameField;
//
//        locationField = new JTextField();
//        locationField.setMaximumSize(new Dimension(WIDTH / 2, 20));
//        fields[fieldNum++] = locationField;
////
//        hoursField = new JTextField();
//        hoursField.setMaximumSize(new Dimension(WIDTH / 2, 20));
//        fields[fieldNum++] = hoursField;

        for (int i = 0; i < labelStrings.length; i++) {  //Associate label/field pairs
            labels[i] = new JLabel(labelStrings[i], JLabel.LEADING);
            labels[i].setLabelFor(fields[i]);
            entryPanel.add(labels[i]);
            entryPanel.add(fields[i]);
            JTextField tf = null;
            tf = (JTextField) fields[i];
            tf.addActionListener(this); //Add listeners to each field.
            tf.addFocusListener(this);
        }
        return entryPanel;
    }

    public JComponent[] setUp(JComponent[] fields) {
        int fieldNum = 0;

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(WIDTH / 2, 20));
        fields[fieldNum++] = nameField;
        //nameField.setColumns(20);
//        fields[fieldNum++] = setField(nameField);
//        fields[fieldNum++] = setField(locationField);
//        fields[fieldNum++] = setField(hoursField);
//
        locationField = new JTextField();
        locationField.setMaximumSize(new Dimension(WIDTH / 2, 20));
        fields[fieldNum++] = locationField;
//
        hoursField = new JTextField();
        hoursField.setMaximumSize(new Dimension(WIDTH / 2, 20));
        fields[fieldNum++] = hoursField;

        return fields;
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
