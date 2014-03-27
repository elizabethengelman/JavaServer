import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class IndexHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;
    OutputStream outputStream;
    String currentDirectory;


    public IndexHandler(){
        generator = new ResponseGenerator();
    }
    public Map<String, byte[]> createResponse() {
        String namesOfFiles = getDirectoryFileNames(currentDirectory, "/");

        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
        generator.setBody(namesOfFiles.getBytes());

        Map<String, byte[]> responsePieces = new LinkedHashMap<String, byte[]>();
        responsePieces.put("header", generator.fullHeader);
        responsePieces.put("body", generator.body);
        return responsePieces;
    }

    public void sendResponse(Map<String, byte[]> responsePieces) {
        try {
            DataOutputStream dOut = new DataOutputStream(outputStream);
            dOut.write(responsePieces.get("header"));
            dOut.write(responsePieces.get("body"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void processResponse(HttpRequest httpRequest, String directory, OutputStream os){
        request = httpRequest;
        currentDirectory = directory;
        outputStream = os;
        Map<String, byte[]> responsePieces = createResponse();
        sendResponse(responsePieces);
    }

    private String getDirectoryFileNames(String directory, String currentPath) {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder(directory, currentPath);
        return directoryBuilder.getLinksOfFiles();
    }
}
