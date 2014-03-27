import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class NotFoundHanlder implements Handler{
    HttpRequest request;
    ResponseGenerator generator;

    public void createResponse(HttpRequest request, String currentDirectory) {
        generator.setStatusLine("404");
        generator.setHeaders();
        generator.setBody("File not found".getBytes());
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
