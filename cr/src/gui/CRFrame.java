package gui;

import javax.swing.*;

public class CRFrame extends JFrame implements Runnable {
    private final int WIDTH = 1300;
    private final int HEIGHT = 1000;

    public CRFrame(){
        super("Centro de Reparações");
    }

    @Override
    public void run() {
        super.setSize(this.WIDTH, this.HEIGHT);
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
