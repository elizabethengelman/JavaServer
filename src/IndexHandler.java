import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class IndexHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;
    String currentDirectory;

    public IndexHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String directory) {
        request = httpRequest;
        currentDirectory = directory;
        String namesOfFiles = getDirectoryFileNames(currentDirectory, "/");
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
        generator.setBody(namesOfFiles.getBytes());
    }

    public void sendResponse(OutputStream outputStream) {
        try {
            byte[] requestHeader = generator.fullHeader;
            byte[] requestBody = generator.body;
            DataOutputStream dOut = new DataOutputStream(outputStream);
            dOut.write(requestHeader);
            dOut.write(requestBody);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private String getDirectoryFileNames(String directory, String currentPath) {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder(directory, currentPath);
        return directoryBuilder.getLinksOfFiles();
    }
}
