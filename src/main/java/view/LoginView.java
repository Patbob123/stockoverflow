package view;

import interface_adapter.user.login.LoginController;
import interface_adapter.user.login.LoginViewModel;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JFrame implements ActionListener, PropertyChangeListener {
    private final LoginViewModel loginViewModel;
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton loginButton = new JButton("log in");
    private final JButton createAccountButton = new JButton("create an account");
    private final JLabel messageLabel = new JLabel();

    @Setter
    private LoginController loginController;
    @Setter
    private CreateAccountView createAccountView;
    @Setter
    private MainMenuView mainMenuView;

    public LoginView(LoginViewModel loginViewModel) {
        super("log in");
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        setupUI();
        setupListeners();

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // username
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        // password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        // message
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        // button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(createAccountButton, gbc);

        add(panel);
    }

    private void setupListeners() {
        loginButton.addActionListener(this);
        createAccountButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            loginController.execute(username, password);
        } else if (e.getSource() == createAccountButton) {
            this.setVisible(false);
            createAccountView.setVisible(true);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginViewModel viewModel = (LoginViewModel) evt.getNewValue();
        messageLabel.setText(viewModel.getMessage());

        if (viewModel.isSuccess()) {
            usernameField.setText("");
            passwordField.setText("");
        }
    }
}