import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.util.Vector;

public class ResultsWindow extends JFrame{
    final int WINDOW_WIDTH=600;
    final int WINDOW_HEIGHT=800;

    public ResultsWindow(String username, String password){
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){}
        setTitle(username);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Database.initializeDB();

        Connection conn;
        Statement stmt;
        Vector<String> columnNames=new Vector();
        columnNames.add("Name");
        columnNames.add("Date");
        columnNames.add("Timestamp");
        JTable table = new JTable(new DefaultTableModel(columnNames,0));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"ID","Timestamp","Date"});
       // table.setRowSorter(new TableRowSorter(model));
        ResultSet resultSet=executeQuery("Select * from access_times", username, password);
        add(table);
        try{
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
                model.addRow(new Object[]{resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)});
            }
        }catch(SQLException e){

        }

    }
    public ResultSet executeQuery(String query,String username, String password) {
        ResultSet resultSet=null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesses", username, password);
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
