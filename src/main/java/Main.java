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

        String usrInput;
        int choice = 0;
        int printedError = 0;
        String filename = "output.txt";
        
        //Basic Menu System
        do {
            System.out.println("Please select an option.");
            usrInput = input.next();
            try { // Tries to convert the input to a intiger. If it can't, it prints the error message and tries the input again.
                choice = Integer.parseInt(usrInput);
            } catch (Exception NumberFormatException) {
                System.out.println("Please only enter 1, 2, 3 or -1");
                printedError = 1; // Stops the error message from being printed twice.
            }
            if (choice > 0 && choice < 4 || choice == -1){
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
                                fileIO.readFromFile(filename);
                        }
                        break;
                    case 2:
                        System.out.println();
                        System.out.println("Updating User Details...");
                        System.out.println("What is the Existing ID?");
                        input.nextLine();
                        String CheckID = input.nextLine();
                        System.out.println("What is the New First Name?");
                        String newFirstName = input.nextLine();;
                        System.out.println("What is the new Last Name?");
                        String newLastName = input.nextLine();;
                        System.out.println("What is the new ID?");
                        String newID = input.nextLine();;
                        System.out.println("What is the new Address?");
                        String newAddress = input.nextLine();;
                        System.out.println("Are you changing your birthday? [Yes = 1, No = 0]");
                        System.out.println("WARNING: You can't change any other value if you change your birthday!");
                        String ChangeBirth = input.nextLine();;
                        String newBirthday = "26-8-2007";
                        if (ChangeBirth.equals("1")){
                            System.out.println("What is the new Birthday?");
                            newBirthday = input.nextLine();;
                        }
                        person.updatePersonalDetails(newID, newFirstName, newLastName, newAddress, newBirthday, CheckID, ChangeBirth, filename);
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
            } else if (printedError == 0){ // Stops the error message from being printed twice.
                System.out.println("Please only enter 1, 2, 3 or -1");
            } else {
                printedError = 0;
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