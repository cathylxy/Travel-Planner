package ui;

import model.Activity;
import model.ActivityList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Method taken from TextInputDemo class in
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// Represents application's create and display travel plan window frame.
public class ActivityUI extends JPanel implements ActionListener, FocusListener {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private ActivityList travelPlanner;
    private boolean createActivity;

    JPanel editPanel;
    JPanel entryPanel;
    JPanel displayPanel;
    JPanel buttonPanel;
    JTextArea display;
    JTextField nameField;
    JTextField locationField;
    JTextField hoursField;

    private static final String JSON_STORE = "./data/travelplanner.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: create and display travel plan UI
    public ActivityUI() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        add(createEditPanel());     //left side panel: edit travel plan
        //createEditPanel().setAlignmentX(Component.LEFT_ALIGNMENT);
        add(createDisplayPanel()); //right side panel: display travel plan

        travelPlanner = new ActivityList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: create edit activity panel
    public JComponent createEditPanel() {
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.PAGE_AXIS));
        editPanel.setPreferredSize(new Dimension(WIDTH / 3 * 2, HEIGHT / 2));
        editPanel.add(createEntryFields());
        editPanel.add(createButtons());
        return editPanel;
    }

    //EFFECTS: create buttons on the panel
    public JComponent createButtons() {
        buttonPanel = new JPanel();
        //buttonPanel.setBorder(BorderFactory.createLineBorder(Color.red));

        //add activity to travel plan
        JButton addButton = new JButton("Add Activity");
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        buttonPanel.add(addButton);

        //delete activity from travel plan
        JButton deleteButton = new JButton("Delete Activity");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("delete");
        buttonPanel.add(deleteButton);

        //save new or modified travel plan
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        buttonPanel.add(saveButton);

        //load existing travel plan
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        loadButton.setActionCommand("load");
        buttonPanel.add(loadButton);

        //clear entry fields
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        clearButton.setActionCommand("clear");
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    // EFFECTS: called when the user clicks the button
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getActionCommand()).equals("clear")) {
            createActivity = false;
            nameField.setText("");
            locationField.setText("");
            hoursField.setText("");
        } else {
            createActivity = true;
            if ((e.getActionCommand()).equals("add")) {
                Activity activity = new Activity(
                        nameField.getText(), locationField.getText(),
                        Integer.valueOf(hoursField.getText()));
                travelPlanner.addActivity(activity);

            } else if ((e.getActionCommand()).equals("delete")) {
                Activity toBeDeleted = travelPlanner.findActivity(nameField.getText()); // find activity by description
                travelPlanner.deleteActivity(toBeDeleted);

            } else if ((e.getActionCommand()).equals("save")) {
                saveTravelPlan();

            } else { //((e.getActionCommand()).equals("load"))
                loadTravelPlan();
            }
            updateDisplays();
        }
    }

    //EFFECTS: create entry fields on the panel
    public JComponent createEntryFields() {
        entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.PAGE_AXIS));
        entryPanel.setPreferredSize(new Dimension(WIDTH / 3 * 2, HEIGHT / 2));
        //entryPanel.setBorder(BorderFactory.createLineBorder(Color.green));

        String[] labelStrings = {"Description: ", "Location: ", "Hours: "};
        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];

        setUp(fields);

        for (int i = 0; i < labelStrings.length; i++) {  // associate label/field pairs
            labels[i] = new JLabel(labelStrings[i], JLabel.LEADING);
            labels[i].setLabelFor(fields[i]);
            entryPanel.add(labels[i]);
            entryPanel.add(fields[i]);
            JTextField tf = (JTextField) fields[i];
            tf.addActionListener(this);
            tf.addFocusListener(this);
        }
        return entryPanel;
    }

    // EFFECTS: create and set up text field
    public JComponent[] setUp(JComponent[] fields) {
        int fieldNum = 0;

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(WIDTH / 2, 20));
        fields[fieldNum++] = nameField;

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
    public JComponent createDisplayPanel() {
        displayPanel = new JPanel(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        //displayPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        displayPanel.add(new JSeparator(JSeparator.VERTICAL)); //separate left and right
        display = new JTextArea();
        updateDisplays();
        //displayPanel.add(display, BorderLayout.CENTER);
        displayPanel.add(display);
        return displayPanel;
    }

    // EFFECTS: display activity on panel
    public void updateDisplays() {
        display.setText(displayActivity());
    }

    // EFFECTS: return all activities in travel planner
    public String displayActivity() {
        String activityString = "";
        if (createActivity) {
            ArrayList<Activity> planner = travelPlanner.getPlanner();
            for (Activity activity : planner) {
                activityString += activity.toString() + "\n";
            }
        }
        return activityString;
    }

    // EFFECTS: saves travel plan to file
    public void saveTravelPlan() {
        try {
            jsonWriter.open();
            jsonWriter.write(travelPlanner);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file");
        }
    }

    // EFFECTS: loads travel plan from file
    public void loadTravelPlan() {
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
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //JOptionPane.showConfirmDialog(null,"Are you sure to close!");
                for (Event event : EventLog.getInstance()) { //print events log
                    System.out.println(event);
                }
            }
//            @Override
//            public void windowOpened(WindowEvent e) {
//                super.windowOpened(e);
//                JOptionPane.showMessageDialog(null, "Welcome to the System");
//            }
        });

        //Add contents to the window.
        frame.add(new ActivityUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
