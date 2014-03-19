import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by elizabethengelman on 3/19/14.
 */
public class FileReader {

    public byte[] readFile(String fileName){
        byte[] imageFileData = null;
        try{
            Path path = Paths.get("../cob_spec/public" + fileName);
            imageFileData = Files.readAllBytes(path);
        }catch(Exception e){
            System.out.println(e);
        }
        return imageFileData;
    }


}
