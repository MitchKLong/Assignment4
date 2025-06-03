import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {
     // Test 1: personID too short (less than 10 characters) - should fail
@Test
public void addPersonInvalidPersonIDLength() {
    Person person = new Person();
    boolean result = person.addPerson("56%&fAB",  // 7 chars only
                                      "Kim",
                                      "Philipps",
                                      "32|Naomi Street|Melbourne|Victoria|Australia",
                                      "15-05-1995");
    assertFalse(result, "Should reject personID with invalid length");
}

// Test 2: personID first two chars not digits between 2-9 - should fail
@Test
public void addPersonInvalidPersonIDStartDigits() {
    Person person = new Person();
    boolean result = person.addPerson("1%&sdfABCD",  // first char is 1 (invalid)
                                      "Jane",
                                      "Marie",
                                      "32|Naomi Street|Melbourne|Victoria|Australia",
                                      "15-05-1995");
    assertFalse(result, "Should reject personID starting with digit outside 2-9");
}

// Test 3: personID missing 2+ special characters between positions 3 and 8 - should fail
@Test
public void addPersonInvalidPersonIDSpecialChars() {
    Person person = new Person();
    boolean result = person.addPerson("56abcdefAB",  // no special chars in positions 3-8
                                      "James",
                                      "Smith",
                                      "32|Naomi Street|Melbourne|Victoria|Australia",
                                      "15-05-1995");
    assertFalse(result, "Should reject personID without at least 2 special chars in positions 3-8");
}

// Test 4: personID last two characters not uppercase letters - should fail
@Test
public void addPersonInvalidPersonIDEndChars() {
    Person person = new Person();
    boolean result = person.addPerson("56%&sdfab12",  // last two chars digits not uppercase letters
                                      "Emily",
                                      "Johnson",
                                      "32|Naomi Street|Melbourne|Victoria|Australia",
                                      "15-05-1995");
    assertFalse(result, "Should reject personID whose last two characters are not uppercase letters");
}

// Test 5: birthdate in wrong format (YYYY-MM-DD) - should fail
@Test
public void addPersonInvalidBirthdateFormat() {
    Person person = new Person();
    boolean result = person.addPerson("56%&sdABCD",
                                      "Clarke",
                                      "Rueda",
                                      "32|Naomi Street|Melbourne|Victoria|Australia",
                                      "1995-05-15");  // wrong format
    assertFalse(result, "Should reject birthdate in wrong format");
}

// Test 1: address changed when person is under 18 - should fail
@Test
public void updatePersonalDetailsAddressChangeUnder18() {
    Person person = new Person();
    // Assume person under 18 with this ID
    person.addPerson(
        "32%&sdABCD",
        "Alice",
        "Santos",
        "17|New St|Melbourne|Victoria|Australia",
        "01-01-2010"
    );
    boolean result = person.updatePersonalDetails(
            "32%&sdABCD",  // ID with first digit 3 (odd)
            "Alice",
            "Santos",
            "10|New St|Melbourne|Victoria|Australia",  // address changed
            "01-01-2010",  // birthdate showing age < 18
            "32%&sdABCD",
            "output.txt");
    assertFalse(result, "Should reject address change if person is under 18");
}

// Test 2: changing ID when first digit of current ID is even - should fail
@Test
public void updatePersonalDetailsChangeIDFirstDigitEven() {
    Person person = new Person();
    person.addPerson("42%&sdABCD", 
                     "Ashley", 
                     "Ramirez", 
                     "32|Riviera Drive|Melbourne|Victoria|Australia", 
                     "15-05-1990"
                     );
    boolean result = person.updatePersonalDetails(
            "42%&sdABCD",  // current ID with even first digit '4'
            "Ashley",
            "Ramirez",
            "32|Riviera Drive|Melbourne|Victoria|Australia",
            "15-05-1990",
            "52%&sdABCD",  // new ID different
            "output.txt");
    assertFalse(result, "Should reject changing ID if first digit of current ID is even");
}

// Test 3: changing only birthday (allowed) - should succeed
@Test
public void updatePersonalDetailsChangeOnlyBirthday() {
    Person person = new Person();
    boolean result = person.updatePersonalDetails(
            "31%&sdABCD",
            "Heath",
            "Moreland",
            "32|Riviera Drive|Melbourne|Victoria|Australia",
            "16-05-1990",  // birthday changed only
            "31%&sdABCD",
            "output.txt");
    assertTrue(result, "Should allow changing only birthday");
}

// Test 4: address changed when person is over 18 - should succeed
@Test
public void updatePersonalDetailsAddressChangeOver18() {
    Person person = new Person();
    person.addPerson("32%&sdABCD", 
                     "Brady", 
                     "Davidson", 
                     "9|Riviera Drive|Melbourne|Victoria|Australia", 
                     "01-01-1990"
                     );
    boolean result = person.updatePersonalDetails(
            "32%&sdABCD",  // current ID with first digit 4 (even)
            "Brady",
            "Davidson",
            "10|Riviera Drive|Melbourne|Victoria|Australia",  // changed address
            "01-01-1990",  // age > 18
            "32%&sdABCD",  // same ID
            "output.txt");
    assertTrue(result, "Should allow address change if person is over 18");
}

// Test 5: changing ID when first digit of current ID is odd - should succeed
@Test
public void updatePersonalDetailsChangeIDFirstDigitOdd() {
    Person person = new Person();
    person.addPerson("31%&sdABCD", 
                     "Jamie", 
                     "Hanson", 
                     "32|Riviera Drive|Melbourne|Victoria|Australia", 
                     "15-05-1990"
                     );
    boolean result = person.updatePersonalDetails(
            "51%&sdABCD",  // first digit 5 (odd)
            "Jamie",
            "Hanson",
            "32|Riviera Drive|Melbourne|Victoria|Australia",
            "15-05-1990",
            "31%&sdABCD",  // new ID allowed
            "output.txt");
    assertTrue(result, "Should allow changing ID if first digit of current ID is odd");
}

// Test 1: date of offense in wrong format - should fail
@Test
public void addDemeritPointsInvalidDateFormat() {
    Person person = new Person();
    String personID = "56%&sdABCD";
    person.addPerson(personID,
                     "Mark",
                     "Quintanilla",
                     "10|Main Rd|Melbourne|Victoria|Australia",
                     "01-01-1990");
    String result = person.addDemeritPoints(personID, 3, "2024-01-01"); // wrong format
    assertEquals("Failed", result, "Should reject offense date in wrong format");
}

// Test 2: demerit points above 6 - should fail
@Test
public void addDemeritPointsAboveRange() {
    Person person = new Person();
    String personID = "58%&sdABCD";
    person.addPerson(personID,
                     "Alanah",
                     "Papin",
                     "10|Main Rd|Melbourne|Victoria|Australia",
                     "01-01-1990");
    String result = person.addDemeritPoints(personID, 7, "01-01-2024");  // points > 6
    assertEquals("Failed", result, "Should reject demerit points above 6");
}

// Test 3: person under 21 becomes suspended after exceeding 6 points in 2 years
@Test
public void addDemeritPointsSuspensionUnder21() {
    Person person = new Person();
    String personID = "59%&sdABCD";
    person.addPerson(personID,
                     "Sophie",
                     "Brown",
                     "10|Main Rd|Melbourne|Victoria|Australia",
                     "01-01-2005"); // under 21 now (age 19 approx)
    person.addDemeritPoints(personID, 4, "01-01-2023");
    person.addDemeritPoints(personID, 3, "01-12-2023"); // total 7 > 6
    assertTrue(person.isSuspended(), "Person under 21 should be suspended after exceeding 6 points in 2 years");
}

// Test 4: person over 21 becomes suspended after exceeding 12 points in 2 years
@Test
public void addDemeritPointsSuspensionOver21() {
    Person person = new Person();
    String personID = "61%&sdABCD";
    person.addPerson(personID,
                     "James",
                     "White",
                     "10|Main Rd|Melbourne|Victoria|Australia",
                     "01-01-1990"); // over 21 now
    person.addDemeritPoints(personID, 6, "01-01-2023");
    person.addDemeritPoints(personID, 7, "01-12-2023"); // total 13 > 12
    assertTrue(person.isSuspended(), "Person over 21 should be suspended after exceeding 12 points in 2 years");    
    }

// Test 5: adding 0 demerit points - should fail
@Test
public void addDemeritPointsZeroPoints() {
    Person person = new Person();
    String personID = "62%&sdABCD";
    person.addPerson(personID,
                     "Sarah",
                     "Claire",
                     "10 Main Road|Melbourne|Victoria|Australia",
                     "01-01-1990");
    String result = person.addDemeritPoints(personID, 0, "01-01-2024");  // points = 0 invalid
    assertEquals("Failed", result, "Should reject adding zero demerit points");
}

}
