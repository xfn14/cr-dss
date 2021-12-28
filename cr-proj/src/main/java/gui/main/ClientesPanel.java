package gui.main;

import sgcr.SGCR;

import javax.swing.*;
import java.awt.*;

public class ClientesPanel extends JPanel {
    private final SGCR sgcr;

    public ClientesPanel(SGCR sgcr){
        super(new GridBagLayout());

        this.sgcr = sgcr;

        super.add(new JLabel("teste"));

        super.setBackground(new Color(0, 255, 0, 255));
    }
}
