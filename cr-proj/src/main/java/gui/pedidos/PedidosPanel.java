package gui.pedidos;

import sgcr.SGCR;

import javax.swing.*;
import java.awt.*;

public class PedidosPanel extends JPanel {
    private final SGCR sgcr;

    public PedidosPanel(SGCR sgcr){
        super(new GridBagLayout());

        this.sgcr = sgcr;

        super.add(new JLabel("heyyyyy"));

        super.setBackground(new Color(0, 0, 255, 255));
    }
}
