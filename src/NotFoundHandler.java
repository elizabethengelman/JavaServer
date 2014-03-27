import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/26/14.
 */
public class NotFoundHandler implements Handler {

    ResponseGenerator generator;

    public NotFoundHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest request, String currentDirectory){
        generator.setStatusLine("404");
        generator.setHeaders(CONTENT_TYPE_HTML_HEADER);
        generator.setBody("Not Found".getBytes());
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

    @Override
    public boolean canHandleRequest(HttpRequest request) {
        return true;
    }
}
