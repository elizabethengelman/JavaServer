import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class FileReader {
    public byte[] readFile(String pathName){
        byte[] imageFileData = null;
        try{
            Path path = Paths.get(pathName);
            System.out.println(path);
            imageFileData = Files.readAllBytes(path);
        }catch(Exception e){
            System.out.println(e);
        }
        return imageFileData;
    }
}
