import java.util.Date;
import java.util.HashMap;

public class Main{

    public static void main(String[] args){
        Person person = new Person();
        FileIO fileIO = new FileIO();
        String firstName = "John";
        String lastName = "Smith";
        String id = "23%%%%%%EE";
        String address = "1|Street|City|Victoria|australia";
        String birthDate = "29-2-2000";
         if (person.addPerson(id, firstName, lastName, address, birthDate)){
                fileIO.readFromFile("output.txt");
         }
    }
}