import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

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
}
