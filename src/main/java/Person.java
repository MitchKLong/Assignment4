import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints;
    private boolean isSuspended;

    public boolean addPerson(String personID, String personFirstName, String personLastName, String personAddress, String personBirthdate){

        //TODO: This method adds information about a person to a TXT file.

        //TODO: COMPLETE
        //Conditions 1: PersonID should be exactly 10 characters long.
        //the first two characters should be numbers between 2 and 9, there should be at least two special characters between characters 3 and 8,
        // and the last two characters should be upper case letters (A-Z) example: "56s_d%fAB"

        //TODO: COMPLETE
        //Condition 2: The address of the Person should follow the following format: Street Number|Street|City|State|Country,
        //The state should only be Victoria. Example: 32|Highland Street|Melbourne|Victoria|Australia

        //TODO: COMPLETE
        //Condition 3: The format of the birth date of the person should follow the following format: DD-MM-YYYY, Example: 15-11-1990

        //TODO: COMPLETE
        //Instruction: If the Person's information meets the above conditions and any other conditions you may want to consider,
        //the information should be inserted into a TXT file, and the addPerson function should return true.
        //Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.

        FileIO fileIO = new FileIO();

        if (personID.length() != 10){
            System.out.println("Id too short");
            return false;
        }
        System.out.println(personID.length());

        if (!verifyID(personID)){
            System.out.println("Id not verified");
            return false;
        }

        if (!verifyAddress(personAddress)){
            System.out.println("Address not verified");
            return false;
        }

        if (!verifyBirthdate(personBirthdate)){
            System.out.println("Birthdate not verified");
            return false;
        }

        if (!fileIO.writeToFile(personID, personFirstName, personLastName, personAddress, personBirthdate, "output.txt", null)){
            System.out.println("Unable to write to file");
            return false;
        }
        return true;
    }

    private boolean verifyID(String ID){
        int specialCharCount = 0;

        //Check Numbers in Pos 1 and Pos 2
        for (int i = 0; i < 2; i++){
            System.out.println(ID.charAt(i) + " ");

            //Check if digit
            if (!Character.isDigit(ID.charAt(i))){
                System.out.println("Character at " + i + "is not a number");
                return false;
            }

            //Check if digit is 2-9
            if ((Integer.parseInt(Character.toString(ID.charAt(i))) < 2) || (Integer.parseInt(Character.toString(ID.charAt(i))) > 9)){
                System.out.println("Digit at " + i + " is not within range 2-9");
                return false;
            }
        }

        //Check for 2 special chars between 3-8
        for (int i = 2; i < 8; i++){
            if (!Character.isLetterOrDigit(ID.charAt(i))  && (!Character.isWhitespace(ID.charAt(i)))){
                specialCharCount += 1;
            }
        }

        System.out.println(specialCharCount);
        if (specialCharCount < 2){
            System.out.println("You must have at least 2 special characters between pos 3-8");
            return false;
        }

        //Check for 2 Uppercase characters at pos 9 and 10
        for (int i  = 8; i<ID.length(); i++){

            if(!Character.isLetter(ID.charAt(i))){
                System.out.println("Character at " + i + " is not a letter");
                return false;
            }


            if (!Character.isUpperCase(ID.charAt(i))){
                System.out.println("Character at " + i + " is not uppercase");
                return false;
            }
        }

        System.out.println("Verified");
        return true;
    }

    private boolean verifyAddress(String address){
        String[] addressArray = address.split("\\|");

        //format: Street Number|Street|City|State|Country

        //The state should only be Victoria
        //Therefore the Country should only be Australia as well

        if (addressArray.length < 5){
            System.out.println("Address input must match Street Number|Street|City|State|Country");
            return false;
        }

        //Check Street Number
        for (int i = 0; i < addressArray[0].length(); i++){
            System.out.println(addressArray[0].charAt(i));
            if (!Character.isDigit(addressArray[0].charAt(i))){
                System.out.println("Street Number must only contain numbers");
                return false;
            }
        }

        //Check State
        System.out.println(addressArray[3]);
        if (!addressArray[3].equalsIgnoreCase("victoria")){
            System.out.println("State does not equal Victoria");
            return false;
        }


        //Check Country
        System.out.println(addressArray[4]);
        if (!addressArray[4].equalsIgnoreCase("australia")){
            System.out.println("Country does not equal Australia");
            return false;
        }


        return true;
    }

    private boolean verifyBirthdate(String birthdate){

        //Days in each month
        int[] daysInMonth = {
                31, // Jan
                28, // Feb
                31, // Mar
                30, // Apr
                31, // May
                30, // Jun
                31, // Jul
                31, // Aug
                30, // Sep
                31, // Oct
                30, // Nov
                31  // Dec
        };

        //format: DD-MM-YYYY, Example: 15-11-1990
        String[] birthdateArray = birthdate.split("-");

        //Check Input Amount
        if (birthdateArray.length != 3) {
            System.out.println("Birthdate input must match: DD-MM-YYYY, Example: 15-11-1990");
            return false;
        }


        try {
            int day = Integer.parseInt(birthdateArray[0]);
            int month = Integer.parseInt(birthdateArray[1]);
            int year = Integer.parseInt(birthdateArray[2]);
            if (day <= 0) {
                System.out.println("Invalid Day");
                return false;
            }

            //Check month
            if (month < 1 || month > 12) {
                System.out.println("Month date must be within 1-12");
                return false;
            }

            //Check year
            if (year < 1900 || year > 2100) {
                System.out.println("Year must be within 1900-2100");
                return false;
            }

            // Check for leap year and adjust February
            if (month == 2 && isLeapYear(year)) {
                System.out.println("Leap year");
                daysInMonth[1] = 29;
            }

            // Validate day against max for that month
            if (!(day <= daysInMonth[month - 1])) {
                System.out.println("Invalid Day provided");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Birthdate input must match: DD-MM-YYYY, Example: 15-11-1990");
            return false;
        }
        return true;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public boolean updatePersonalDetails(String newID, String newFirstName, String newLastName, String newAddress, String newBirthday, String CheckID, String filename){
        String ID = "none";
        String firstName = "none";
        String lastName = "none";
        String address = "none";
        String birthdate = "none";

        FileIO fileIO = new FileIO();
        String[] inputStr = fileIO.readFromFile(filename);
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-M-yyyy");

        
        if (newID.length() != 10){
            System.out.println("Id too short");
            return false;
        }
        System.out.println(newID.length());

        if (!verifyAddress(newAddress)){
            System.out.println("Address not verified");
            return false;
        }

        if (!verifyBirthdate(newBirthday)){
            System.out.println("Birthdate not verified");
            return false;
        }
        for(int i = 0; i < inputStr.length - 1; ++i){
            try{
                String[] file = inputStr[i].split(" ");
                if (CheckID.equals(file[0])){
                    ID = file[0];
                    firstName = file[1];
                    lastName = file[2];
                    address = file[3];
                    birthdate = file[4];
                    break;
                }
            }
            catch (NullPointerException e){
                System.out.println("Search ID does not exist in file");
                return false;
            }
        }

        if (!newID.equals(ID) || !newFirstName.equals(firstName) || !newLastName.equals(lastName) || !newAddress.equals(address)){
            System.out.println("Other infomation can not be changed if changing birthday.");
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(birthdate, DTF);

        Date currDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date birth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        long diffInMillies = Math.abs(currDate.getTime() - birth.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long diffYears = diff / 365;

        if (diffYears < 18 && !newAddress.equals(address)){
            System.out.println("Address can not be changed if user is under 18.");
            return false;
        }

        String checker = ID.replaceAll("(\\d+).+", "$1");
        int checkint = Integer.parseInt(checker.toString());
        if ((checkint % 2) == 0 && !newID.equals(ID)){
            System.out.println("ID begins with an even number, cannot change ID.");
            return false;
        }

        return true;
    }
}