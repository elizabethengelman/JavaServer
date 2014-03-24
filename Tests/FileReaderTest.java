import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
* Created by elizabethengelman on 3/24/14.
*/
public class FileReaderTest {

    @Test
    public void testReadFile(){
        FileReader reader = new FileReader();
        byte[] fileContents = reader.readFile("Tests/test_file");
        String fileContentsString = new String(fileContents);
        String testFileContents = "test line 1\ntest line 2\n";
        assertEquals(testFileContents, fileContentsString);
    }
}