package gui;

import gui.admin.AdminPanel;
import gui.clientes.ClientesPanel;
import gui.pedidos.PedidosPanel;
import trabalhadores.Gestor;
import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

public class CRFrame extends JFrame implements Runnable, ActionListener {
    private final Logger LOGGER = Logger.getLogger("CR");
    private final int WIDTH = 800, HEIGHT = 600;
    private final LoginFrame loginFrame;
    private final Trabalhador trabalhador;
    private boolean running = true;

    private JPanel sidePanel;
    private ButtonGroup buttonGroup;
    private JRadioButton pedidosButton;
    private JRadioButton clientesButton;
    private JRadioButton adminButton;

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ClientesPanel clientesPanel;
    private PedidosPanel pedidosPanel;
    private AdminPanel adminPanel;

    public CRFrame(LoginFrame loginFrame, Trabalhador trabalhador) {
        Dimension dimension = new Dimension(this.WIDTH, this.HEIGHT);

        this.loginFrame = loginFrame;
        this.trabalhador = trabalhador;

        this.initSidePanel();
        this.initMainPanel();

        super.setLayout(new BorderLayout());
        super.add(this.sidePanel, BorderLayout.WEST);
        super.add(this.mainPanel);

        super.setTitle("Centro de Reparações | " + this.trabalhador.getIdTrabalhador());
        super.setSize(dimension);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginFrame.setVisible(true);
                loginFrame.setLocationRelativeTo(null);
            }
        });
    }

    @Override
    public void run() {
        super.setVisible(true);
        this.cardLayout.show(this.mainPanel, "pedidosPanel");
    }

    public void initSidePanel() {
        this.sidePanel = new JPanel(new GridBagLayout());
        this.sidePanel.setSize(new Dimension(this.WIDTH / 4, this.HEIGHT));
        this.sidePanel.setBackground(new Color(255, 0, 0, 255));
        this.sidePanel.setVisible(true);

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 2, 5, 2);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.NORTH;

        this.buttonGroup = new ButtonGroup();

        JLabel welcome = new JLabel("Bem-vindo, " + this.trabalhador.getIdTrabalhador());
        grid.gridx = 0;
        grid.gridy = 0;
        this.sidePanel.add(welcome, grid);

        this.pedidosButton = new JRadioButton("Pedidos");
        this.pedidosButton.setSelected(true);
        this.pedidosButton.setActionCommand("pedidosButton");
        this.pedidosButton.addActionListener(this);
        grid.gridy = 1;
        this.sidePanel.add(this.pedidosButton, grid);

        this.clientesButton = new JRadioButton("Clientes");
        this.clientesButton.setSelected(false);
        this.clientesButton.setActionCommand("clientesButton");
        this.clientesButton.addActionListener(this);
        grid.gridy = 2;
        this.sidePanel.add(this.clientesButton, grid);

        if (this.trabalhador instanceof Gestor) {
            this.adminButton = new JRadioButton("Admin");
            this.adminButton.setSelected(false);
            this.adminButton.setActionCommand("adminButton");
            this.adminButton.addActionListener(this);
            grid.gridy = 3;
            this.sidePanel.add(this.adminButton, grid);
            this.buttonGroup.add(this.adminButton);
        }

        this.buttonGroup.add(this.pedidosButton);
        this.buttonGroup.add(this.clientesButton);
    }

    private void initMainPanel() {
        this.pedidosPanel = new PedidosPanel(this.loginFrame.getSgcr(), this.trabalhador);
        this.clientesPanel = new ClientesPanel(this.loginFrame.getSgcr());
        this.adminPanel = new AdminPanel(this.loginFrame.getSgcr());
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.mainPanel.add(this.clientesPanel, "clientesPanel");
        this.mainPanel.add(this.pedidosPanel, "pedidosPanel");
        if (this.trabalhador instanceof Gestor)
            this.mainPanel.add(this.adminPanel, "adminPanel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("pedidosButton")) {
            this.cardLayout.show(this.mainPanel, "pedidosPanel");
        } else if (e.getActionCommand().equals("clientesButton")) {
            this.cardLayout.show(this.mainPanel, "clientesPanel");
        } else if (e.getActionCommand().equals("adminButton")) {
            this.cardLayout.show(this.mainPanel, "adminPanel");
        }
    }

    public void stop() {
        this.running = false;
    }
}
