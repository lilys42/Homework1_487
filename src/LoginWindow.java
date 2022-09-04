/*
    LoginWindow asks for a username and password, then passes them to the ResultsWindow constructor.
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class LoginWindow extends JFrame {
    final int WINDOW_WIDTH=300;
    final int WINDOW_HEIGHT=200;

    JTextField user;
    JPasswordField password;

    public LoginWindow(){
        setTitle("Login");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel userPanel=new JPanel();
        userPanel.add(new JLabel("Username"));
        user=new JTextField(10);
        userPanel.add(user);
        add(userPanel, BorderLayout.NORTH);

        JPanel passwordPanel=new JPanel();
        passwordPanel.add(new JLabel("Password"));
        password=new JPasswordField(10);
        passwordPanel.add(password);
        add(passwordPanel,BorderLayout.CENTER);

        JPanel buttonPanel=new JPanel();
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        JButton loginButton=new JButton();
        loginButton.setText("Login");
        buttonPanel.add(loginButton);
        add(buttonPanel,BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResultsWindow(user.getText(),String.valueOf(password.getPassword()));
                dispatchEvent(new WindowEvent(LoginWindow.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        setVisible(true);
    }
}
