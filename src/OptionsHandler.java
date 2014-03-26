import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/26/14.
 */
public class OptionsHandler implements Handler {
    HttpRequest request;
    ResponseGenerator generator;
    String currentDirectory;

    public OptionsHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String directory) {
        request = httpRequest;
        currentDirectory = directory;
        generator.setStatusLine("200");
        generator.setHeaders("Allow: GET,HEAD,POST,OPTIONS,PUT");
        generator.setBody();
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
}
