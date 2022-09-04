import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.EventListener;
import java.util.Vector;

public class ResultsWindow extends JFrame{
    final int WINDOW_WIDTH=300;
    final int WINDOW_HEIGHT=600;

    JTable resultsTable;
    JScrollPane scrollPane;
    JTextField idField;
    JTextField timestampFromField;
    JTextField timestampToField;
    JTextField dateField;
    String username;
    String password;
    boolean connected;

    public ResultsWindow(String username, String password){
        this.username=username;
        this.password=password;

        //Initialize window properties
        setTitle(username);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create panel with search fields and buttons
        JPanel searchPanel=new JPanel();
        BoxLayout boxlayout = new BoxLayout(searchPanel, BoxLayout.Y_AXIS);
        searchPanel.setLayout(boxlayout);
        //searchPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        //Create panel for ID field and add it to searchPanel
        idField=new JTextField(10);
        JPanel idPanel=new JPanel();
        idPanel.add(new JLabel("ID"));
        idPanel.add(idField);
        searchPanel.add(idPanel,BorderLayout.NORTH);

        //Create panel for Timestamp range and add it to searchPanel
        timestampFromField=new JTextField(20);
        timestampToField=new JTextField(20);
        JPanel timePanel=new JPanel();
        timePanel.add(new JLabel("From Time"));
        timePanel.add(timestampFromField);
        timePanel.add(new JLabel("To Time"));
        timePanel.add(timestampToField);
        searchPanel.add(timePanel,BorderLayout.CENTER);

        //Add a search button to searchPanel
        JPanel buttonPanel=new JPanel();
        searchPanel.add(buttonPanel,BorderLayout.SOUTH);
        JButton searchButton=new JButton();
        searchButton.setText("Search");
        buttonPanel.add(searchButton);

        //Add a clear button to searchPanel
        JButton clearButton=new JButton();
        clearButton.setText("Clear");
        buttonPanel.add(clearButton);

        //Set click action for the search button
        searchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButtonAction();
            }
        });

        //Set click action for the clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTable(resultsTable,"Select * from access_times",username,password);
            }
        });

        //Create table
        Vector<String> columnNames=new Vector();
        columnNames.add("Id");
        columnNames.add("Timestamp");
        resultsTable = new JTable(new DefaultTableModel(columnNames,0));

        //Fill table with the whole database, and add it to the frame
        fillTable(resultsTable,"Select * from access_times",username,password);
        scrollPane=new JScrollPane(resultsTable);
        add(scrollPane,BorderLayout.SOUTH);
        resultsTable.setAutoCreateRowSorter(true);

        if(!connected)
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        //Add the search panel to the frame
        add(searchPanel,BorderLayout.NORTH);
        pack();
        setVisible(true);
    }
    public ResultSet executeQuery(String query,String username, String password) {
        ResultSet resultSet=null;
        connected=false;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesses", username, password);
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
            connected=true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Unable To Access Database");
        }
        return resultSet;
    }
    public void fillTable(JTable table,String query, String username, String password){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet resultSet=executeQuery(query, username, password);
        try{
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
                model.addRow(new Object[]{resultSet.getString(1),resultSet.getString(2)});
            }
        }catch(Exception e){
        }
        table.revalidate();
    }
    public void searchButtonAction(){
        StringBuilder query=new StringBuilder();
        query.append("Select * from access_times where ");

        if(!idField.getText().equals(""))
            query.append("student_id="+idField.getText());
        else
            query.append("true ");
        if(!timestampFromField.getText().equals(""))
            query.append(" and timestamp>'"+timestampFromField.getText()+"'");
        else
            query.append(" and true ");
        if(!timestampToField.getText().equals(""))
            query.append(" and timestamp<'"+timestampToField.getText()+"'");
        else
            query.append(" and true ");
        System.out.println(query);
        fillTable(resultsTable,query.toString(),username,password);
    }
}
