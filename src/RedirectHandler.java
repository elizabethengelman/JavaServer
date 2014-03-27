import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class RedirectHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;

    public RedirectHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String currentDirectory) {
        request = httpRequest;
        generator.setStatusLine("307");
        generator.setHeaders("Location: http://localhost:5000/");
        request.setPath("/");
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
