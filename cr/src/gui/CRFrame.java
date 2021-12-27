package gui;

import sgcr.SGCR;

import javax.swing.*;
import java.awt.*;

public class CRFrame extends JFrame implements Runnable {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private JPanel toRender = null;
    private SGCR sgcr;
    private boolean running = true;

    public CRFrame(SGCR sgcr){
        super("Centro de Reparações");
        this.sgcr = sgcr;
    }

    @Override
    public void run() {
        this.toRender = new LoginPanel(WIDTH, HEIGHT);
        super.add(toRender, BorderLayout.CENTER);

        super.setSize(this.WIDTH, this.HEIGHT);
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        while(running){

        }
    }
    
    public void stop(){
        this.running = false;
    }
}
