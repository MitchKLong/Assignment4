import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
}
