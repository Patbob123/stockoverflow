package view;

import interface_adapter.Signup.SignupController;
import interface_adapter.Signup.SignupState;
import interface_adapter.Signup.SignupViewModel;
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


public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final SignupController signupController;
    private final ViewManagerModel viewManagerModel;

    // UI Components
    private final JTextField usernameInputField = new JTextField();
    private final JPasswordField passwordInputField = new JPasswordField();
    private final JPasswordField repeatPasswordInputField = new JPasswordField();

    private final JButton signUpButton;
    private final JButton cancelButton;

    public SignupView(SignupController controller, SignupViewModel signupViewModel, ViewManagerModel viewManagerModel) {
        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(SingleStockViewModel.BG_COLOUR);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(SingleStockViewModel.CARD_COLOUR);
        cardPanel.setPreferredSize(new Dimension(450, 600));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 1),
                new EmptyBorder(50, 50, 50, 50)
        ));

        JLabel title = new JLabel("Create Account");
        title.setFont(SingleStockViewModel.TITLE_FONT.deriveFont(32f)); // 更大的标题
        title.setForeground(SingleStockViewModel.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Join Stock Overflow today.");
        subtitle.setFont(SingleStockViewModel.BASE_FONT.deriveFont(14f));
        subtitle.setForeground(SingleStockViewModel.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(title);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(subtitle);
        cardPanel.add(Box.createVerticalStrut(40)); // 标题与表单的间距

        cardPanel.add(createLabeledField(SignupViewModel.USERNAME_LABEL, usernameInputField));
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(createLabeledField(SignupViewModel.PASSWORD_LABEL, passwordInputField));
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(createLabeledField(SignupViewModel.REPEAT_PASSWORD_LABEL, repeatPasswordInputField));
        cardPanel.add(Box.createVerticalStrut(40)); // 表单与按钮的间距

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonsPanel.setBackground(SingleStockViewModel.CARD_COLOUR);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setMaximumSize(new Dimension(400, 50)); // 按钮条高度

        cancelButton = createStyledButton(SignupViewModel.CANCEL_BUTTON_LABEL, SingleStockViewModel.SECONDARY_COLOUR);
        signUpButton = createStyledButton(SignupViewModel.SIGNUP_BUTTON_LABEL, SingleStockViewModel.PRIMARY_COLOUR);

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(signUpButton);

        cardPanel.add(buttonsPanel);

        this.add(cardPanel);

        setupListeners();
    }


    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(SingleStockViewModel.CARD_COLOUR);
        panel.setMaximumSize(new Dimension(400, 70));

        JLabel label = new JLabel(labelText);
        label.setForeground(SingleStockViewModel.TEXT_SECONDARY);
        label.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD, 14f));

        field.setFont(SingleStockViewModel.BASE_FONT.deriveFont(16f));
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
        button.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT.deriveFont(16f));
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

    private void setupListeners() {
        signUpButton.addActionListener(evt -> {
            SignupState currentState = signupViewModel.getState();
            signupController.execute(
                    currentState.getUsername(),
                    currentState.getPassword(),
                    currentState.getRepeatPassword()
            );
        });

        cancelButton.addActionListener(evt -> {
            usernameInputField.setText("");
            passwordInputField.setText("");
            repeatPasswordInputField.setText("");

            viewManagerModel.setActiveView("log in");
            viewManagerModel.firePropertyChanged();
        });

        KeyListener keyListener = new KeyListener() {
            @Override public void keyTyped(KeyEvent e) { updateState(); }
            @Override public void keyPressed(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) { updateState(); }

            private void updateState() {
                SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                currentState.setPassword(new String(passwordInputField.getPassword()));
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }
        };

        usernameInputField.addKeyListener(keyListener);
        passwordInputField.addKeyListener(keyListener);
        repeatPasswordInputField.addKeyListener(keyListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}