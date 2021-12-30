package gui;

import javax.swing.*;
import java.awt.*;

public abstract class PrettyPanel extends JPanel {
    private final GridBagLayout layout;
    private final GridBagConstraints constraints;

    public PrettyPanel(){
        super();
        this.layout = new GridBagLayout();
        this.constraints = new GridBagConstraints();
        this.constraints.weightx = 1.0;
        this.constraints.weighty = 1.0;
        this.constraints.fill = GridBagConstraints.HORIZONTAL;
        super.setLayout(layout);

        this.addComponents();
        this.addActionListener();
    }

    public abstract void addComponents();

    public abstract void addActionListener();

    public void addComponent(Component component, int row, int column) {
        this.constraints.gridy = row;                        // set gridy
        this.constraints.gridx = column;                     // set gridx
        this.constraints.gridwidth = 1;                      // set width
        this.constraints.gridheight = 1;                     // set height
        this.layout.setConstraints(component, constraints);  // set constraints of component on layout
        super.add(component);                                // add component to frame
    }

    public void addComponent(Component component, int row, int column,
                             int width, int height) {
        this.constraints.gridy = row;                        // set gridy
        this.constraints.gridx = column;                     // set gridx
        this.constraints.gridwidth = width;                  // set width
        this.constraints.gridheight = height;                // set height
        this.layout.setConstraints(component, constraints);  // set constraints of component on layout
        super.add(component);                                // add component to frame
    }
}
