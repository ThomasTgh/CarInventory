package View.Form.Login;

import Controller.Session.SessionManager;
import Model.Data.UserData;
import View.Form.IBaseForm;
import View.MainWindow;
import View.Utility.SpringUtilities;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

public class LoginForm extends IBaseForm
{
    private final JTextField userNameTextField = new JTextField();
    private final JPasswordField passwordTextField = new JPasswordField();
    private final MainWindow mainWindow;

    private UserData logIn() throws NoSuchAlgorithmException
    {
        String userName = userNameTextField.getText();
        String password = String.valueOf(passwordTextField.getPassword());
        return SessionManager.get().logIn(userName, password);
    }

    @Override
    public void bindButtons(JButton okButton, JButton cancelButton)
    {
        okButton.addActionListener(e ->
        {
            try
            {
                UserData userRecord = logIn();
                if (userRecord != null)
                {
                    JOptionPane.showMessageDialog(
                            null, String.format("Welcome, %s!", userRecord.getUserName()),
                            "Login Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    mainWindow.updateMenuState(MainWindow.VEHICLE_ID);
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(
                            null, "Invalid username or password!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(
                        null, ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    public LoginForm(MainWindow mainWindow)
    {
        super();

        bindButtons(okButton, cancelButton);

        setTitle("Login Form");

        addComponentPair("User Name", userNameTextField);
        addComponentPair("Password", passwordTextField);

        buildForm(mainWindow);

        this.mainWindow = mainWindow;
    }
}
