import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        Person person = new Person();
        FileIO fileIO = new FileIO();


        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Road Registry System Victoria.");
        

        System.out.println(" 1.  Register A User.");
        System.out.println(" 2.  Update A User's Details.");
        System.out.println(" 3.  Add Demerit Points To A User.");
        System.out.println("-1.  Exit.");

        int choice;
        
        //TODO: ADD EXCEPTION HANDLING

        //Basic Menu System
        do {
            System.out.println("Please select an option.");
            choice = input.nextInt();
    
            switch (choice){
                case 1:
                    //TODO: PROMPT USER FOR VALS
                    System.out.println();
                    System.out.println("Registering A User...");
                    String firstName = "John";
                    String lastName = "Smith";
                    String id = "23%%%%%%EE";
                    String address = "1|Street|City|Victoria|australia";
                    String birthDate = "29-2-2000";
                    if (person.addPerson(id, firstName, lastName, address, birthDate)){
                            fileIO.readFromFile("output.txt");
                    }
                    break;
                case 2:
                    System.out.println();
                    System.out.println("Updating User Details...");
                    //TODO: IMPLEMENT FUNCTION
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Add Demerit Points...");
                    //TODO: IMPLEMENT FUNCTION
                    break;
                case -1:
                    System.out.println();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid Input.");
                    break;
            }

            if (choice != -1){
                System.out.println();
                System.out.println(" 1.  Register A User.");
                System.out.println(" 2.  Update A User's Details.");
                System.out.println(" 3.  Add Demerit Points To A User.");
                System.out.println("-1.  Exit.");
            }
            
        } while (choice != -1);
        input.close();
    }
}