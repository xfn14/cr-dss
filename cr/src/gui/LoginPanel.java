package gui;

import sgcr.SGCR;
import trabalhadores.Trabalhador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener {
    private SGCR sgcr;
    private Trabalhador trabalhador = null;

    private JTextField usernameInput;
    private JTextField passwordInput;
    private JButton login;

    public LoginPanel(int width, int height){
        super(new GridBagLayout());
        super.setPreferredSize(new Dimension(width, height));
        super.setVisible(true);
        initLoginPanel();
    }

    private void initLoginPanel(){
        GridBagConstraints grid = new GridBagConstraints();

        JLabel username = new JLabel("Username:");
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 0;
        super.add(username, grid);

        this.usernameInput = new JTextField(16);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 1;
        grid.gridy = 0;
        super.add(this.usernameInput, grid);

        JLabel password = new JLabel("Password:");
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 1;
        super.add(password, grid);

        this.passwordInput = new JTextField(16);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 1;
        grid.gridy = 1;
        super.add(this.passwordInput, grid);

        this.login = new JButton("Login");
        this.login.addActionListener(this);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 2;
        super.add(login, grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.trabalhador = this.sgcr.doLogin(
                this.usernameInput.getText(),
                this.passwordInput.getText()
        );
        if(this.trabalhador == null){
            this.login.setText("no");
        }else{
            this.login.setText("yes");
        }
    }
}
