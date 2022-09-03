import java.util.Scanner;

public class SelectMode {
    public static void main(String[] args){
        Database.initializeDB();
        System.out.println("Enter 'I' to input id's, or 'G' to view the GUI");
        Scanner input=new Scanner(System.in);
        String selection=input.next();

        if(selection.equals("I"))
            Database.enterId();
        else if(selection.equals("G"))
            GUIMode.loadGUI();
    }
}
