import java.util.Scanner;

public class SelectMode {
    public static void main(String[] args){
        Database.initializeDB();
        System.out.println("Enter 'E' to input id's, or 'V' to view the GUI");
        Scanner input=new Scanner(System.in);
        String selection=input.next();

        if(selection.equals("E"))
            Database.enterId();
        //else if(selection.equals("V"))
            //Similar method for select mode
    }
}
