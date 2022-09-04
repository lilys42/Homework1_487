import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectMode {
    static String DB_URL="jdbc:mysql://localhost:3306";
    static String username;
    static String password;
    public static void main(String[] args){
        getLoginCredentials();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, username, password);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE ACCESSES";
            stmt.executeUpdate(sql);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesses", username, password);
            stmt = conn.createStatement();
            sql = "create table access_times(student_id int, timestamp timestamp, access_date date, PRIMARY KEY" +
                    "(student_id,timestamp))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
        }

        System.out.println("Enter 'I' to input id's, or 'G' to view the GUI");
        Scanner input=new Scanner(System.in);
        String selection=input.next();

        if(selection.equals("I"))
            new insertMode(username,password);
        else if(selection.equals("G"))
            new LoginWindow();
    }
    public static void getLoginCredentials(){
        try{
            File loginFile=new File("C:\\Users\\Lily\\Desktop\\Login.txt");
            Scanner file=new Scanner(loginFile);
            username=file.nextLine();
            password=file.nextLine();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
