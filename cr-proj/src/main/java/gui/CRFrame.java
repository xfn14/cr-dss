package gui;

import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

public class CRFrame extends JFrame implements Runnable {
    private final Logger LOGGER = Logger.getLogger("CR");
    private final int WIDTH = 800, HEIGHT = 600;
    private final LoginFrame loginFrame;
    private Trabalhador trabalhador;
    private JPanel panel;
    private JLabel label;

    public CRFrame(LoginFrame loginFrame, Trabalhador trabalhador){
        super("Centro de Reparações");

        this.loginFrame = loginFrame;
        this.trabalhador = trabalhador;
        this.panel = new JPanel();
    }

    @Override
    public void run() {
        super.setSize(this.WIDTH, this.HEIGHT);
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginFrame.setVisible(true);
            }
        });
    }
}
