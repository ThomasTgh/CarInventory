package Controller.Session;

import Controller.Utility.PasswordUtilities;
import Model.Data.UserData;
import Model.List.UserList;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class SessionManager
{
    UserData currentUserRecord = null;
    final String USERNAME_FILE = "JoeCarSession_1.dat";
    final String PASSWORD_FILE = "JoeCarSession_2.dat";
    final String SALT_FILE = "JoeCarSession_3.dat";
    private SessionManager() { }
    private static final SessionManager instance = new SessionManager();
    public static SessionManager get() { return instance; }

    public UserData getCurrentUser()
    {
        return currentUserRecord;
    }

    public boolean isLoggedIn()
    {
        return currentUserRecord != null;
    }

    public void logOut()
    {
        currentUserRecord = null;
    }

    public static String loadStringFromFile(String fileName)
    {
        try
        {
            return Files.readString(Path.of(fileName));
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(
                    null, ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }
    public static void saveStringToFile(String fileName, String data)
    {
        try
        {
            FileOutputStream file = new FileOutputStream(fileName);
            file.write(data.getBytes());
            file.close();
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(
                    null, ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveSession()
    {
        if (!isLoggedIn()) { return; }
        saveStringToFile(USERNAME_FILE, currentUserRecord.getUserName());
        saveStringToFile(PASSWORD_FILE, currentUserRecord.getPassword());
        saveStringToFile(SALT_FILE, currentUserRecord.getSalt());
    }

    public void loadSession()
    {
        if (!new File(USERNAME_FILE).isFile() || !new File(PASSWORD_FILE).isFile() || !new File(SALT_FILE).isFile()) { return; }
        var userName = loadStringFromFile(USERNAME_FILE);
        var password = loadStringFromFile(PASSWORD_FILE);
        var salt = loadStringFromFile(SALT_FILE);

        for (var obj : UserList.get())
        {
            UserData userRecord = (UserData) obj;
            if (userRecord.getUserName().equals(userName) && userRecord.getPassword().equals(password))
            {
                currentUserRecord = userRecord;
                break;
            }
        }
    }

    public UserData logIn(String userName, String password) throws NoSuchAlgorithmException
    {
        for (var obj : UserList.get())
        {
            UserData userRecord = (UserData) obj;
            if (userRecord.getUserName().equals(userName) && userRecord.getPassword().equals(PasswordUtilities.sha256Salted(password, userRecord.getSalt())))
            {
                currentUserRecord = userRecord;
                saveSession();
                return userRecord;
            }
        }
        return null;
    }
}
