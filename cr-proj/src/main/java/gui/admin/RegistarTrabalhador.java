package gui.admin;

import exceptions.JaExisteException;
import exceptions.PasswordErradaException;
import gui.PrettyFrame;
import sgcr.SGCR;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarTrabalhador extends PrettyFrame implements ActionListener {
    private final SGCR sgcr;

    private JComboBox<String> cargosBox;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JPasswordField confirmPasswordInput;
    private JButton confirm;
    private JLabel status;

    public RegistarTrabalhador(SGCR sgcr) {
        super("Registar Trabalhadores", 700, 500);
        this.sgcr = sgcr;
    }

    public void addComponents() {
        String[] cargos = {"Funcionario", "Tecnico", "Gestor"};
        this.cargosBox = new JComboBox<>(cargos);
        this.cargosBox.setSelectedIndex(0);
        this.usernameInput = new JTextField(16);
        this.passwordInput = new JPasswordField(16);
        this.confirmPasswordInput = new JPasswordField(16);
        this.confirm = new JButton("Registar");
        this.status = new JLabel("");

        super.addComponent(new JLabel("Cargo"), 0, 0);
        super.addComponent(this.cargosBox, 0, 1);
        super.addComponent(new JLabel("Username"), 1, 0);
        super.addComponent(this.usernameInput, 1, 1);
        super.addComponent(new JLabel("Password"), 2, 0);
        super.addComponent(this.passwordInput, 2, 1);
        super.addComponent(new JLabel("Confirma Password"), 3, 0);
        super.addComponent(this.confirmPasswordInput, 3, 1);
        super.addComponent(this.confirm, 4, 0, 2, 1);
        super.addComponent(this.status, 5, 0, 2, 1);
    }

    public void addActionListener() {
        this.confirm.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            String cargo = (String) this.cargosBox.getSelectedItem();
            String username = this.usernameInput.getText();
            String password = String.valueOf(this.passwordInput.getPassword());
            String confPass = String.valueOf(this.confirmPasswordInput.getPassword());
            if (cargo == null) return;
            boolean result = false;
            switch (cargo) {
                case "Tecnico":
                    try {
                        result = this.sgcr.registarTecnico(username, password, confPass);
                    } catch (JaExisteException | PasswordErradaException exception) {
                        String exceptionMessage = exception.getMessage();
                        //TODO Adicionar mensagem de erro
                    }

                    break;
                case "Funcionario":
                    try {
                        result = this.sgcr.registarFuncionario(username, password, confPass);
                    } catch (JaExisteException | PasswordErradaException exception) {
                        String exceptionMessage = exception.getMessage();
                        //TODO Adicionar mensagem de erro
                    }
                    break;
                case "Gestor":
                    try {
                        result = this.sgcr.registarGestor(username, password, confPass);
                    } catch (JaExisteException | PasswordErradaException exception) {
                        String exceptionMessage = exception.getMessage();
                        //TODO Adicionar mensagem de erro
                    }
                    break;
                default:
                    result = false;
                    break;
            }

            if (result) {
                this.status.setText("");
                super.dispose();
            } else this.status.setText("<html><font color=red>Trabalhador Invalido!</font></html>");
        }
    }
}
