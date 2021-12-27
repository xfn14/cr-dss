package gui;

import sgcr.SGCR;
import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class LoginFrame extends JFrame implements Runnable, ActionListener {
    private final Logger LOGGER = Logger.getLogger("CR");
    private final int WIDTH, HEIGHT;
    private SGCR sgcr;
    private final JPanel panel;
    private JLabel status;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JButton login;

    public LoginFrame(SGCR sgcr, int width, int height){
        super("Centro de Reparações");

        this.sgcr = sgcr;
        this.WIDTH = width;
        this.HEIGHT = height;

        this.panel = new JPanel();
        this.panel.setSize(new Dimension(width, height));
        this.panel.setVisible(true);
        initLoginPanel();
    }

    @Override
    public void run() {
        super.add(this.panel, BorderLayout.CENTER);
        super.setSize(this.WIDTH, this.HEIGHT);
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initLoginPanel(){
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(0, 2, 0, 2);

        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.CENTER;
        this.status = new JLabel("");
        grid.gridx = 0; grid.gridy = 0;
        grid.gridwidth = 2;
        this.panel.add(status, grid);

        JLabel username = new JLabel("Username:");
        grid.gridx = 0; grid.gridy = 1;
        this.panel.add(username, grid);

        this.usernameInput = new JTextField(16);
        grid.gridx = 1; grid.gridy = 1;
        this.panel.add(this.usernameInput, grid);

        JLabel password = new JLabel("Password:");
        grid.gridx = 0; grid.gridy = 2;
        this.panel.add(password, grid);

        this.passwordInput = new JPasswordField(16);
        grid.gridx = 1; grid.gridy = 2;
        this.panel.add(this.passwordInput, grid);

        this.login = new JButton("Login");
        this.login.addActionListener(this);
        grid.gridx = 0; grid.gridy = 3;
        grid.gridwidth = 2;
        this.panel.add(this.login, grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.login)){
            Trabalhador trabalhador = this.sgcr.doLogin(
                    this.usernameInput.getText(),
                    String.valueOf(this.passwordInput.getPassword())
            );
            if(trabalhador == null)
                this.status.setText("Login Invalido!");
            else{
                // TODO Change frame
                this.status.setText("Login valido");
            }
        }
    }

    public SGCR getSgcr() {
        return this.sgcr;
    }
}
