package gui;

import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

public class CRFrame extends JFrame implements Runnable {
    private final Logger LOGGER = Logger.getLogger("CR");
    private final int WIDTH = 800, HEIGHT = 600;
    private final LoginFrame loginFrame;
    private final Trabalhador trabalhador;
    private JPanel sidePanel;
    private JPanel mainPanel;
    private JList<String> listaPedidos;

    public CRFrame(LoginFrame loginFrame, Trabalhador trabalhador){
        super("Centro de Reparações");

        this.loginFrame = loginFrame;
        this.trabalhador = trabalhador;

        this.initSidePanel();
        this.initMainPanel();
    }

    @Override
    public void run() {
        super.add(this.sidePanel, BorderLayout.CENTER);
        super.add(this.mainPanel, BorderLayout.CENTER);

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

    public void initSidePanel(){
        this.sidePanel = new JPanel(new GridBagLayout());
        this.sidePanel.setSize(new Dimension(this.WIDTH / 4, this.HEIGHT));
        this.sidePanel.setBackground(new Color(0,0,0,255));
        this.sidePanel.setVisible(true);

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcome = new JLabel("Bem-vindo, " + this.trabalhador.getIdTrabalhador());
        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.NORTH;
        this.sidePanel.add(welcome, grid);


    }

    public void initMainPanel(){
        this.mainPanel = new JPanel(new GridBagLayout());
        this.mainPanel.setSize(new Dimension(3 * this.WIDTH / 4, this.HEIGHT));
        this.mainPanel.setBackground(new Color(255,255,255,255));
        this.mainPanel.setVisible(true);
    }
}
