import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

        
    
    //TODO: Have file writing system append to file not just rewrite file
    //TODO: Have file writing system check for duplicate ID's before writing for file as no two people can have the same ID

    //Write to File
    public boolean writeToFile(String ID, String firstName, String lastName, String address, String birthDate, String fileName){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(ID + " " + firstName + " " + lastName + " " + address + " " + birthDate);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Unsucessfully wrote to file");
            return false;
        }
        System.out.println("Successfully wrote ID: " + ID + " to file");
        return true;
    }

    //Read entire file
    public boolean readFromFile(String fileName){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read file");
            return false;
        }
        System.out.println("Successfully read file");
        return true;
    }
}   

