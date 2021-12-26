package gui;

import javax.swing.*;

public class CRFrame extends JFrame implements Runnable {
    public CRFrame(){
        super("Centro de Reparações");

    }

    @Override
    public void run() {
        super.setSize(800, 600);
        super.pack();
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
