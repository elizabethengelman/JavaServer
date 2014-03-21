import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by elizabethengelman on 3/21/14.
 */
public class DirectoryBuilder {
    String currentDirectory;
    public DirectoryBuilder(){
        currentDirectory = "../cob_spec/public";
    }

    public List<File> getFilesInCurrentDirectory(){
        File f = new File(currentDirectory);
        List<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        return files;
    }

    public String getNamesOfFiles(){
        List<File> filesInDirectory = getFilesInCurrentDirectory();
        String filesAsList = "";
        for (File file: filesInDirectory){
            filesAsList += file.getName() + "\n";
        }
        System.out.println(filesAsList);
        return filesAsList;
    }



}
