import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
* Created by elizabethengelman on 3/21/14.
*/
public class DirectoryBuilderTest {
   @Test
   public void testGetFilesInCurrentDirectory(){
       String fileNamesString =
               "<a href=/file1>file1</a><br>"+
               "<a href=/file2>file2</a><br>"+
               "<a href=/form>form</a><br>"+
               "<a href=/image.gif>image.gif</a><br>"+
               "<a href=/image.jpeg>image.jpeg</a><br>" +
               "<a href=/image.png>image.png</a><br>" +
               "<a href=/logs>logs</a><br>" +
               "<a href=/partial_content.txt>partial_content.txt</a><br>" +
               "<a href=/testfolder>testfolder</a><br>" +
               "<a href=/text-file.txt>text-file.txt</a><br>";
       DirectoryBuilder directoryBuilder = new DirectoryBuilder("../cob_spec/public", "/");
       assertEquals(fileNamesString, directoryBuilder.getLinksOfFiles());
   }
}
