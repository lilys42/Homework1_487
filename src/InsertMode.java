/*
    insertMode allows the user to enter id's into the database.
 */
import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;

public class InsertMode {
    static final String DB_URL="jdbc:mysql://localhost:3306";
     String username;
     String password;

    public InsertMode(String username, String password){
        this.username=username;
        this.password=password;

        Scanner input=new Scanner(System.in);
        String selection="S";
        do{
            System.out.println("Enter id, or Q to quit:");
            selection=input.next();
            if(selection.equals("Q"))
                break;
            String student_id=selection;

            //Add an entry to the database table
            Calendar calendar = Calendar.getInstance();
            Timestamp timestamp=new Timestamp(calendar.getTimeInMillis());
            Date date=new Date(calendar.getTimeInMillis());
            insert(extractId(student_id),timestamp.toString(),date.toString());
        } while(!selection.equalsIgnoreCase("Q"));
    }
    public void insert(String student_id, String timestamp, String access_date){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesses", username, password);
            Statement stmt= conn.createStatement();
            String sql="insert into access_times values("+student_id+",'"+timestamp+"','"+access_date+"')";
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public String extractId(String id){
        for(int i=0;i<id.length()-2;i++){
            if(id.charAt(i)=='%'&&id.charAt(i+1)=='A'){
                return id.substring(i+2,i+11);
            }
        }
        return id;
    }
}
