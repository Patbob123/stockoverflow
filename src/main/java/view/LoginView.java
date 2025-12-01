package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.singlestock.SingleStockViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends PaddedView<LoginViewModel, LoginController> implements ActionListener, PropertyChangeListener {

    public static final String VIEW_NAME = "LoginMenu";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField();
    private final JPasswordField passwordInputField = new JPasswordField();

    private final JButton logInButton;
    private final JButton signUpButton;

    public LoginView(LoginViewModel loginViewModel) {
        super(loginViewModel);
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setPreferredSize(new Dimension(400, 500));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LoginViewModel.BORDER_COLOUR, 1),
                new EmptyBorder(50, 50, 50, 50)
        ));

        JLabel title = new JLabel("Welcome Back");
        title.setFont(LoginViewModel.TITLE_FONT);
        title.setForeground(LoginViewModel.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(title);
        cardPanel.add(Box.createVerticalStrut(40));

        cardPanel.add(createLabeledField(loginViewModel.USERNAME_LABEL, usernameInputField));
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(createLabeledField(loginViewModel.PASSWORD_LABEL, passwordInputField));
        cardPanel.add(Box.createVerticalStrut(40));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonsPanel.setBackground(LoginViewModel.CARD_COLOUR);
        buttonsPanel.setMaximumSize(new Dimension(300, 50));

        signUpButton = createStyledButton("Sign Up", LoginViewModel.SECONDARY_COLOUR);
        logInButton = createStyledButton(loginViewModel.LOGIN_BUTTON_LABEL, LoginViewModel.PRIMARY_COLOUR);

        buttonsPanel.add(signUpButton);
        buttonsPanel.add(logInButton);

        cardPanel.add(buttonsPanel);
        this.add(cardPanel);

        signUpButton.addActionListener(e -> {
            this.getChangeViewController().changeView(SignupView.VIEW_NAME);
        });

        logInButton.addActionListener(evt -> {
            LoginState currentState = this.getViewModel().getState();
            this.getController().execute(currentState.getUsername(), currentState.getPassword());
        });

        addInputListeners();
    }


    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(LoginViewModel.CARD_COLOUR);
        panel.setMaximumSize(new Dimension(300, 65));

        JLabel label = new JLabel(labelText);
        label.setForeground(LoginViewModel.TEXT_SECONDARY);
        label.setFont(LoginViewModel.BASE_FONT.deriveFont(Font.BOLD, 14f));

        field.setFont(LoginViewModel.BASE_FONT.deriveFont(16f));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(LoginViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(bg); }
        });
        return button;
    }

    private void addInputListeners() {
        KeyListener listener = new KeyListener() {
            public void keyTyped(KeyEvent e) { updateState(e); }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) { updateState(e); }

            private void updateState(KeyEvent e) {
                LoginState s = loginViewModel.getState();
                s.setUsername(usernameInputField.getText());
                s.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(s);
            }
        };
        usernameInputField.addKeyListener(listener);
        passwordInputField.addKeyListener(listener);
    }

    @Override public void actionPerformed(ActionEvent e) {}
    @Override public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }
}