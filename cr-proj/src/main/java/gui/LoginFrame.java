package gui;

import emailHandler.Email;
import sgcr.SGCR;
import trabalhadores.Trabalhador;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFrame extends JFrame implements Runnable, ActionListener {
    private final Logger LOGGER = Logger.getLogger("CR");
    public static final String RESOURCES_PATH = "src/main/resources/";
    private final int WIDTH = 600, HEIGHT = 400;
    private final SGCR sgcr;
    private JPanel panel;
    private JLabel status;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JButton login;

    public LoginFrame(SGCR sgcr, Email email) {
        super("Centro de Reparações");

        this.sgcr = sgcr;
        this.initLoginPanel();

        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(this.WIDTH, this.HEIGHT);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                email.cancelTimer();
                try {
                    FileUtils.objectToFile(sgcr, RESOURCES_PATH + "sgcr.obj");
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Erro ao converter SGCR para ficheiro.", ex);
                }
            }
        });
    }

    @Override
    public void run() {
        super.setVisible(true);
    }

    private void initLoginPanel() {
        this.panel = new JPanel(new GridBagLayout());
        this.panel.setSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.panel.setVisible(true);

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        this.status = new JLabel("", SwingConstants.CENTER);
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        this.panel.add(this.status, grid);

        JLabel username = new JLabel("Username");
        grid.gridwidth = 1;
        grid.gridx = 0;
        grid.gridy = 1;
        this.panel.add(username, grid);

        this.usernameInput = new JTextField(16);
        this.usernameInput.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 1;
        this.panel.add(this.usernameInput, grid);

        JLabel password = new JLabel("Password");
        grid.gridx = 0;
        grid.gridy = 2;
        this.panel.add(password, grid);

        this.passwordInput = new JPasswordField(16);
        this.passwordInput.addActionListener(this);
        grid.gridx = 1;
        grid.gridy = 2;
        this.panel.add(this.passwordInput, grid);

        this.login = new JButton("Login");
        this.login.addActionListener(this);
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 2;
        this.panel.add(this.login, grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.login) || e.getSource().equals(this.usernameInput) || e.getSource().equals(this.passwordInput)) {
            Trabalhador trabalhador = this.sgcr.doLogin(
                    this.usernameInput.getText(),
                    String.valueOf(this.passwordInput.getPassword())
            );
            if (trabalhador == null)
                this.status.setText("<html><font color=red>Login Invalido!</font></html>");
            else {
                this.resetFields();
                CRFrame crFrame = new CRFrame(this, trabalhador);
                Thread thread = new Thread(crFrame);
                thread.start();
                super.setVisible(false);
            }
        }
    }

    private void resetFields() {
        this.usernameInput.setText("");
        this.passwordInput.setText("");
        this.status.setText("");
    }

    public SGCR getSgcr() {
        return this.sgcr;
    }
}
