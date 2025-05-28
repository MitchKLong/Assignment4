import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.FileWriter;
import java.io.IOException;

public class PersonTest {
    

    //Valid ID, ADDRESS, BIRTHDATE
    @Test
    public void addPersonValidTest(){
        Person person = new Person();
        boolean result = person.addPerson("23%%%%%%EE", "John", "Smith" ,"1|Street|City|Victoria|australia", "29-2-2000");

        assertTrue(result);
    }

    //Invalid ID, VALID ADDRESS, VALID BIRTHDATE
    @Test
    public void addPersonInvalidIDTest(){
        Person person = new Person();
        boolean result = person.addPerson("11%%%%%%EE", "John", "Smith", "1|Street|City|Victoria|australia", "29-2-2000");

        assertFalse(result);
    }

    //Valid ID, INVAlID ADDRESS, BIRTHDATE
    @Test
    public void addPersonInvalidAddressTest(){
        Person person = new Person();
        boolean result = person.addPerson("23%%%%%%EE", "John", "Smith", "1|Street|City|New South Wales|australia", "29-2-2000");

        assertFalse(result);
    }

    //Valid ID, ADDRESS, INVALID BIRTHDATE
    @Test
    public void addPersonInvalidBirthdateTest(){
        Person person = new Person();
        boolean result = person.addPerson("23%%%%%%EE", "John", "Smith", "1|Street|City|Victoria|australia", "0-2-2000");

        assertFalse(result);
    }

    /**
     * Test case for adding demerit points to a person
     * Tests the following scenarios:
     * 1. Valid demerit points addition
     * 2. Invalid demerit points (points > 6)
     * 3. Invalid date format
     * 4. Non-existent person
     */
    @Test
    public void testAddDemeritPoints() {
        Person person = new Person();
        
        // Setup: Add a test person first with a unique ID
        String personID = "37%%%%%%EB"; // Make ID unique
        String firstName = "Jane";
        String lastName = "Johnson";
        String address = "123|Hank Street|Melbourne|Victoria|Australia";
        String birthdate = "29-2-2000";
        
        // Test 1: Add person successfully
        boolean addPersonResult = person.addPerson(personID, firstName, lastName, address, birthdate);
        assertTrue(addPersonResult, "Failed to add test person");
        
        // Test 2: Add valid demerit points
        String result = person.addDemeritPoints(personID, 6, "11-11-2022");
        assertEquals("Success", result, "Failed to add valid demerit points");
        
        // Test 3: Add invalid demerit points (points > 6)
        result = person.addDemeritPoints(personID, 7, "11-11-2022");
        assertEquals("Failed", result, "Should fail for points > 6");
        
        // Test 4: Add demerit points with invalid date format
        result = person.addDemeritPoints(personID, 6, "11/11/2022");
        assertEquals("Failed", result, "Should fail for invalid date format");
        
        // Test 5: Add demerit points for non-existent person
        result = person.addDemeritPoints("99%%%%%%XX", 6, "11-11-2022");
        assertEquals("Failed", result, "Should fail for non-existent person");
        
        // Verify demerit points file format
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("demerit_points.txt"));
            String header = reader.readLine();
            reader.readLine(); // Skip the empty line after header
            String demeritEntry = reader.readLine();
            reader.close();
            
            // Verify header format
            assertEquals("personID|offenseDate|points", header.trim(), "Header format is incorrect");
            
            // Verify demerit entry format
            String expectedEntry = personID + "|11-11-2022|6";
            assertEquals(expectedEntry, demeritEntry.trim(), "Demerit entry format is incorrect");
            
        } catch (java.io.IOException e) {
            fail("Failed to read demerit points file: " + e.getMessage());
        }
    }

    /**
     * Test case 1: Valid date format and demerit points
     * Tests Condition 1 and 2: Valid date format (DD-MM-YYYY) and valid demerit points (1-6)
     */
    @Test
    public void testAddDemeritPointsValidInput() {
        Person person = new Person();
        String personID = "37%%%%%%EB";
        String firstName = "Jane";
        String lastName = "Johnson";
        String address = "123|Hank Street|Melbourne|Victoria|Australia";
        String birthdate = "29-2-2000";
        
        person.addPerson(personID, firstName, lastName, address, birthdate);
        String result = person.addDemeritPoints(personID, 6, "11-11-2022");
        assertEquals("Success", result, "Should accept valid date format and demerit points");
    }

    /**
     * Test case 2: Invalid date format
     * Tests Condition 1: Invalid date format (should be DD-MM-YYYY)
     */
    @Test
    public void testAddDemeritPointsInvalidDateFormat() {
        Person person = new Person();
        String personID = "37%%%%%%EB";
        String result = person.addDemeritPoints(personID, 6, "11/11/2022");
        assertEquals("Failed", result, "Should reject invalid date format");
    }

    /**
     * Test case 3: Invalid demerit points
     * Tests Condition 2: Demerit points must be between 1-6
     */
    @Test
    public void testAddDemeritPointsInvalidPoints() {
        Person person = new Person();
        String personID = "37%%%%%%EB";
        String result = person.addDemeritPoints(personID, 7, "11-11-2022");
        assertEquals("Failed", result, "Should reject demerit points > 6");
    }

    /**
     * Test case 4: Suspension for person under 21
     * Tests Condition 3: Person under 21 with total points > 6 in 2 years
     */
    @Test
    public void testAddDemeritPointsSuspensionUnder21() {
        Person person = new Person();
        String personID = "37%%%%%%EB";
        String firstName = "Jane";
        String lastName = "Johnson";
        String address = "123|Hank Street|Melbourne|Victoria|Australia";
        String birthdate = "29-2-2000"; // Person is under 21
        
        person.addPerson(personID, firstName, lastName, address, birthdate);
        
        // Add first demerit point
        String result1 = person.addDemeritPoints(personID, 4, "11-11-2022");
        assertEquals("Success", result1, "Should add first demerit point");
        
        // Add second demerit point that exceeds limit
        String result2 = person.addDemeritPoints(personID, 3, "12-11-2022");
        assertEquals("Success", result2, "Should add second demerit point");
        
        assertTrue(person.isSuspended(), "Person under 21 should be suspended when total points > 6");
    }

    /**
     * Test case 5: Suspension for person over 21
     * Tests Condition 3: Person over 21 with total points > 12 in 2 years
     */
    @Test
    public void testAddDemeritPointsSuspensionOver21() {
        Person person = new Person();
        String personID = "37%%%%%%EB";
        String firstName = "Jane";
        String lastName = "Johnson";
        String address = "123|Hank Street|Melbourne|Victoria|Australia";
        String birthdate = "29-2-1980"; // Person is over 21
        
        person.addPerson(personID, firstName, lastName, address, birthdate);
        
        // Add multiple demerit points to exceed 12 points
        String result1 = person.addDemeritPoints(personID, 6, "11-11-2022");
        assertEquals("Success", result1, "Should add first set of demerit points");
        
        String result2 = person.addDemeritPoints(personID, 6, "12-11-2022");
        assertEquals("Success", result2, "Should add second set of demerit points");
        
        String result3 = person.addDemeritPoints(personID, 1, "13-11-2022");
        assertEquals("Success", result3, "Should add third set of demerit points");
        
        assertTrue(person.isSuspended(), "Person over 21 should be suspended when total points > 12");
    }
}
