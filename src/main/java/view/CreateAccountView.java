package view;

import interface_adapter.user.create.CreateAccountController;
import interface_adapter.user.create.CreateAccountViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateAccountView extends JFrame implements ActionListener, PropertyChangeListener {
    private final CreateAccountViewModel createAccountViewModel;
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton createButton = new JButton("create an account");
    private final JButton backButton = new JButton("Back to Login");
    private final JLabel messageLabel = new JLabel(" ");

    private CreateAccountController createAccountController;
    private LoginView loginView;

    public CreateAccountView(CreateAccountViewModel createAccountViewModel) {
        super("Create a new account");
        this.createAccountViewModel = createAccountViewModel;
        this.createAccountViewModel.addPropertyChangeListener(this);

        setupUI();
        setupListeners();

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("username:"), gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        panel.add(createButton, gbc);
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        this.add(panel);
    }

    private void setupListeners() {
        createButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    public void setCreateAccountController(CreateAccountController createAccountController) {
        this.createAccountController = createAccountController;
    }
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            createAccountController.execute(username, password);
        } else if (e.getSource() == backButton) {
            this.setVisible(false);
            loginView.setVisible(true);
            // 清空状态
            usernameField.setText("");
            passwordField.setText("");
            messageLabel.setText(" ");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreateAccountViewModel viewModel = (CreateAccountViewModel) evt.getNewValue();
        messageLabel.setText(viewModel.getMessage());

        if (viewModel.isSuccess()) {
            // After successful creation, it will automatically return to the login interface
            JOptionPane.showMessageDialog(this, "The account has been created successfully");
            this.setVisible(false);
            loginView.setVisible(true);
            // Clear the input box
            usernameField.setText("");
            passwordField.setText("");
        }
    }
}