import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

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

        if (!newID.equals(ID) && !verifyID(newID)) {
        System.out.println("New ID format is invalid");
        return false;   
        }

        if (!verifyBirthdate(newBirthday)){
            System.out.println("Birthdate not verified");
            return false;
        }
        for(int i = 0; i < inputStr.length; ++i){
            try{
                String[] file = inputStr[i].split("  ");
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
    // FIX birthday-only function
    boolean onlyBirthdayChanged = !newBirthday.equals(birthdate) && 
                              newID.equals(ID) && 
                              newFirstName.equals(firstName) && 
                              newLastName.equals(lastName) && 
                              newAddress.equals(address);

    // If only birthday is changed, allow it and return true
    if (onlyBirthdayChanged) {
    return true;
    }

    // If birthday is changed along with other fields, reject
    if (!newBirthday.equals(birthdate)) {
    System.out.println("Cannot change birthday along with other information.");
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

    /**
     * Adds demerit points for a person and tracks their suspension status.
     * 
     * This function:
     * 1. Validates the offense date format (DD-MM-YYYY)
     * 2. Validates demerit points (must be between 1-6)
     * 3. Verifies the person exists in the system
     * 4. Calculates total demerit points within 2 years
     * 5. Updates suspension status based on age and points:
     *    - Under 21: suspended if total points > 6 in 2 years
     *    - Over 21: suspended if total points > 12 in 2 years
     * 6. Records the demerit points in demerit_points.txt
     * 
     * @param personID The unique identifier of the person (must exist in output.txt)
     * @param demeritPoints Number of points to add (must be between 1-6)
     * @param offenseDate Date of the offense in DD-MM-YYYY format
     * @return "Success" if demerit points were added successfully, "Failed" otherwise
     */
    public String addDemeritPoints(String personID, int demeritPoints, String offenseDate){
        // Validate date format DD-MM-YYYY
        if (!offenseDate.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$")) {
            System.out.println("Date format validation failed");
            return "Failed";
        }

        // Validate demerit points (whole number 1-6)
        if (demeritPoints < 1 || demeritPoints > 6) {
            System.out.println("Demerit points validation failed");
            return "Failed";
        }

        // First verify the person exists in output.txt
        FileIO fileIO = new FileIO();
        String[] persons = fileIO.readFromFile("output.txt");
        System.out.println("Number of lines read: " + persons.length);
        String birthdate = null;
        boolean personFound = false;
        for (String line : persons) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            // First split by personID to get the rest of the line
            String[] mainParts = line.split(personID, 2);
            if (mainParts.length != 2) continue;
            
            // Split the remaining part by spaces
            String[] remainingParts = mainParts[1].trim().split("  ");
            if (remainingParts.length < 3) continue;  // Need at least firstName, lastName, and birthdate
            
            // Get the birthdate (last part)
            birthdate = remainingParts[remainingParts.length - 1];
            personFound = true;
            System.out.println("Person found with ID: " + personID);
            System.out.println("Birthdate found: " + birthdate);
            break;
        }
        if (!personFound || birthdate == null) {
            System.out.println("Person not found with ID: " + personID);
            return "Failed";
        }

        // Parse birthdate and offenseDate to calculate age
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate birthDate;
        LocalDate offense;
        try {
            birthDate = LocalDate.parse(birthdate, formatter);
            offense = LocalDate.parse(offenseDate, formatter);
            System.out.println("Successfully parsed dates");
        } catch (Exception e) {
            System.out.println("Date parsing failed: " + e.getMessage());
            return "Failed";
        }

        // Calculate age at current time
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - birthDate.getYear();
        if (currentDate.getDayOfYear() < birthDate.getDayOfYear()) {
            age--;
        }
        System.out.println("Calculated age: " + age);

        // Read existing demerit points from demerit_points.txt
        int totalPointsIn2Years = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("demerit_points.txt"));
            String line;
            // Skip header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length != 3) continue;
                
                if (parts[0].equals(personID)) {
                    try {
                        LocalDate d = LocalDate.parse(parts[1], formatter);
                        int pts = Integer.parseInt(parts[2]);
                        // Only count points within 2 years of the current offense
                        if (!d.isAfter(offense)) {
                            if (!d.isBefore(offense.minusYears(2))) {
                                totalPointsIn2Years += pts;
                                System.out.println("Adding points: " + pts + " from date: " + d);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error processing demerit points: " + e.getMessage());
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            // If file doesn't exist, create it with header
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("demerit_points.txt"));
                writer.write("personID|offenseDate|points\n");
                writer.close();
            } catch (IOException ex) {
                System.out.println("Error creating demerit points file: " + ex.getMessage());
                return "Failed";
            }
        }

        // Add current demerit points to total
        totalPointsIn2Years += demeritPoints;
        System.out.println("Total points in 2 years: " + totalPointsIn2Years);
        System.out.println("Age: " + age + ", Points: " + totalPointsIn2Years);

        // Apply suspension rules based on age
        if (age < 21 && totalPointsIn2Years > 6) {
            System.out.println("Setting suspension for under 21 with points > 6");
            this.isSuspended = true;
        } else if (age >= 21 && totalPointsIn2Years > 12) {
            System.out.println("Setting suspension for over 21 with points > 12");
            this.isSuspended = true;
        }
        System.out.println("Final suspension status: " + this.isSuspended);

        // Append new demerit point to demerit_points.txt
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("demerit_points.txt", true));
            writer.write("\n" + personID + "|" + offenseDate + "|" + demeritPoints);
            writer.close();
            System.out.println("Successfully wrote to demerit points file");
        } catch (IOException e) {
            System.out.println("IO Exception while writing to file: " + e.getMessage());
            return "Failed";
        }

        return "Success";
    }

    public boolean isSuspended() {
        return isSuspended;
    }

}