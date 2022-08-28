import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;

public class EnterID {
    static final String DB_URL="jdbc:mysql://localhost:3306";
    static final String USER = "root";
    static final String PASS = "Summer321#";

    public static void main(String[] args){
        //Initialize the database and the access_times table, if the database does not already exist
        initializeDB();
        Scanner input=new Scanner(System.in);
        String selection="S";
        do{
            System.out.println("Click any key to enter an id, or Q to quit:");

            selection=input.next();
            if(!selection.equalsIgnoreCase("Q")){
                 //Get student id
                 System.out.println("Enter student id:");
                 long student_id=input.nextLong();

                 //Add an entry to the database table
                 Calendar calendar = Calendar.getInstance();
                 Timestamp timestamp=new Timestamp(calendar.getTimeInMillis());
                 Date date=new Date(calendar.getTimeInMillis());
                 insert(student_id,timestamp.toString(),date.toString());
            }
        } while(!selection.equalsIgnoreCase("Q"));
    }
    public static void initializeDB() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE ACCESSES";
            stmt.executeUpdate(sql);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesses", USER, PASS);
            stmt = conn.createStatement();
            sql = "create table access_times(student_id int, timestamp timestamp, access_date date, PRIMARY KEY" +
                    "(student_id,timestamp))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
           // e.printStackTrace();
        }
    }
    public static void insert(Long student_id, String timestamp, String access_date){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesses", USER, PASS);
            Statement stmt= conn.createStatement();
            String sql="insert into access_times values("+student_id+",'"+timestamp+"','"+access_date+"')";
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
