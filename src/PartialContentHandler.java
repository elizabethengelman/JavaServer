import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class PartialContentHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;
    FileReader reader = new FileReader();
    String currentDirectory;

    public PartialContentHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String directory) {
        request = httpRequest;
        currentDirectory = directory;
        createPartialContentResponse(reader);
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

    private void createPartialContentResponse(FileReader reader) {
        byte[] file = reader.readFile(currentDirectory + request.getPath());
        String range = request.getRange();
        String partialContentLength = range.substring(range.indexOf("-")+1);
        String originalContentLength = Integer.toString(file.length);
        Date date = new Date();
        generator.setStatusLine("206");
        generator.setHeaders("Content-Type: text/plain",
                "Content-Range: bytes " + range + "/" + originalContentLength,
                "Date: " + date.toString(),
                "Content-Length: " + partialContentLength);
        generator.setBody(file);
    }
}
