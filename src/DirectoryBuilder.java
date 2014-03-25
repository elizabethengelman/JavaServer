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
        System.out.println("this is the current directory withint the DirectoryBuilder " + currentDirectory);
        List<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        return files;
    }

    public String getLinksOfFiles(){
        List<File> filesInDirectory = getFilesInCurrentDirectory();
        String filesAsList = "";
        for (File file: filesInDirectory){
            if (path == "/"){
                filesAsList += "<a href=" + path + file.getName()+ ">" + file.getName() + "</a><br>";
            }else{
                filesAsList += "<a href=" + path + "/" + file.getName()+ ">" + file.getName() + "</a><br>";
            }
        }
        System.out.println(filesAsList);
        return filesAsList;
    }
}
