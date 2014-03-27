import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by elizabethengelman on 3/21/14.
 */
public class DirectoryBuilder {
    String currentDirectory;
    String path;
    public DirectoryBuilder(String directory, String currentPath){
        currentDirectory = directory;
        path = currentPath;
    }

    public List<File> getFilesInCurrentDirectory(){
        File f = new File(currentDirectory);
        List<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        return files;
    }

    public String getLinksOfFiles(){
        List<File> filesInDirectory = getFilesInCurrentDirectory();
        String filesAsList = "";

        System.out.println("this is the path: " + path);
        for (File file: filesInDirectory){
            if (path.equals("/")){
                filesAsList += "<a href=" + path + file.getName()+ ">" + file.getName() + "</a><br>";
            }else{
                filesAsList += "<a href=" + path + "/" + file.getName()+ ">" + file.getName() + "</a><br>";
            }
        }
        return filesAsList;
    }
}
