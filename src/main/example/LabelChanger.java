package example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabelChanger extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField field;

    public LabelChanger() {
        super("The title"); // Creates a new, initially invisible Frame with the specified title.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Sets the behavior for when the window is closed
        setPreferredSize(new Dimension(400, 90)); // Sets window size
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout()); // Add a layout manager so that the button is not placed on top of the label
        JButton btn = new JButton("Change");
        btn.setActionCommand("myButton"); //Sets the action command for this button.
        btn.addActionListener(this); //Adds an ActionListener to the button.
        // Sets "this" object as an action listener for btn
        // so that when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click
        label = new JLabel("flag");
        field = new JTextField(5);
        add(field);
        add(btn);
        add(label); //add the label to the frame
        pack();     //size the window and lay out its contents
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            label.setText(field.getText());
        }
    }

    public static void main(String[] args) {
        new LabelChanger();
    }
}
