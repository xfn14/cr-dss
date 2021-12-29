package gui.admin;

import sgcr.SGCR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarTrabalhador extends JFrame implements ActionListener {
    private final SGCR sgcr;

    private JPanel panel;
    private JComboBox<String> cargosBox;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JPasswordField confirmPasswordInput;
    private JButton confirm;
    private JLabel status;

    public RegistarTrabalhador(SGCR sgcr) {
        super("Registar Trabalhadores");
        this.sgcr = sgcr;

        this.initPanel();
        super.add(this.panel, BorderLayout.CENTER);

        super.setSize(700, 500);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setVisible(true);
    }

    private void initPanel() {
        this.panel = new JPanel(new GridBagLayout());

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        JLabel cargo = new JLabel("Cargo");
        grid.gridx = 0;
        grid.gridy = 0;
        this.panel.add(cargo, grid);

        String[] cargos = {"Funcionario", "Tecnico", "Gestor"};
        this.cargosBox = new JComboBox<>(cargos);
        this.cargosBox.setSelectedIndex(0);
        grid.gridx = 1;
        grid.gridy = 0;
        this.panel.add(this.cargosBox, grid);

        JLabel username = new JLabel("Username");
        grid.gridx = 0;
        grid.gridy = 1;
        this.panel.add(username, grid);

        this.usernameInput = new JTextField(16);
        grid.gridx = 1;
        grid.gridy = 1;
        this.panel.add(this.usernameInput, grid);

        JLabel password = new JLabel("Password");
        grid.gridx = 0;
        grid.gridy = 2;
        this.panel.add(password, grid);

        this.passwordInput = new JPasswordField(16);
        grid.gridx = 1;
        grid.gridy = 2;
        this.panel.add(this.passwordInput, grid);

        JLabel passwordConf = new JLabel("Confirma Password");
        grid.gridx = 0;
        grid.gridy = 3;
        this.panel.add(passwordConf, grid);

        this.confirmPasswordInput = new JPasswordField(16);
        grid.gridx = 1;
        grid.gridy = 3;
        this.panel.add(this.confirmPasswordInput, grid);

        this.confirm = new JButton("Registar");
        this.confirm.addActionListener(this);
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 4;
        this.panel.add(this.confirm, grid);

        this.status = new JLabel("");
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 5;
        this.panel.add(this.status, grid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.confirm)) {
            String cargo = (String) this.cargosBox.getSelectedItem();
            String username = this.usernameInput.getText();
            String password = String.valueOf(this.passwordInput.getPassword());
            String confPass = String.valueOf(this.confirmPasswordInput.getPassword());
            if (cargo == null) return;
            boolean result;
            switch (cargo) {
                case "Tecnico":
                    result = this.sgcr.registarTecnico(username, password, confPass);
                    break;
                case "Funcionario":
                    result = this.sgcr.registarFuncionario(username, password, confPass);
                    break;
                case "Gestor":
                    result = this.sgcr.registarGestor(username, password, confPass);
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
