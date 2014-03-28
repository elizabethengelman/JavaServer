import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by elizabethengelman on 3/28/14.
 */
public class FileWriterTest {
    @Test
    public void testWriteFile(){
        FileWriter fileWriter = new FileWriter();
        FileReader fileReader = new FileReader();
        String testFileContents = "test content to be written\n";

        fileWriter.writeToFile("Tests/test_file", "test content to be written");
        byte[] fileContentFromFile = fileReader.readFile("Tests/test_file");
        System.out.println(new String(fileContentFromFile));
        assertArrayEquals(testFileContents.getBytes(), fileContentFromFile);
    }




}
