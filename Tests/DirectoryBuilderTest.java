import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by elizabethengelman on 3/21/14.
 */
public class DirectoryBuilderTest {
   @Test
   public void testGetFilesInCurrentDirectory(){
       String currentFilesString = "file1\n" +"file2\n" +"image.gif\n" +
               "image.jpeg\n" +"image.png\n" +
               "partial_content.txt\n" +"text-file.txt\n";
       DirectoryBuilder directoryBuilder = new DirectoryBuilder();
       assertEquals(currentFilesString, directoryBuilder.getNamesOfFiles());
   }



}
