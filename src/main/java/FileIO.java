import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
    //Write to File
    public boolean writeToFile(String ID, String firstName, String lastName, String address, String birthDate, String fileName, String CheckID){
        boolean returnval = false;
        if (CheckID == null){
            String line;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                StringBuffer inputBuffer = new StringBuffer();
                while ((line = reader.readLine()) != null){
                    String[] currLine = line.split(" ");
                    if (ID.equals(currLine[0])){
                        System.out.println("ID Already Exists");
                        reader.close();
                        return false;
                    }
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                inputBuffer.append(ID + " " + firstName + " " + lastName + " " + address + " " + birthDate);
                String inputStr = inputBuffer.toString();
                writer.write(inputStr);
                writer.close();
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
                System.out.println("Unsucessfully wrote to file");
                return false;
            }
            System.out.println("Successfully wrote ID: " + ID + " to file");
            returnval = true;
        } else if (CheckID != null){
            String line;
            String replaceLine = "none";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                StringBuffer inputBuffer = new StringBuffer();
                while ((line = reader.readLine()) != null){
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                    replaceLine = line;
                }
                reader.close();
                String inputStr = inputBuffer.toString();
                inputStr = inputStr.replace(replaceLine, ID + " " + firstName + " " + lastName + " " + address + " " + birthDate);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(inputStr);
                writer.close();
                System.out.println(inputStr);
            } catch (IOException e){
                System.out.println("File Does Not Exist");
                return false;
            }
        }
        return returnval;
    }

    //Read entire file
    public String[] readFromFile(String fileName){
        String[] inputStr = new String[1000];
        String[] notTrue = new String[]{"false"};
        int i = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null){
                if (line.trim().isEmpty()) continue;
                System.out.println(line);
                inputStr[i++] = line;
            }
            reader.close();
            
            // Create a new array with only the actual lines read
            String[] result = new String[i];
            System.arraycopy(inputStr, 0, result, 0, i);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read file");
            return notTrue;
        }
    }
}   