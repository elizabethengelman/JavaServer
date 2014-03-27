import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class AuthenticatedHandler implements Handler {

    HttpRequest request;
    ResponseGenerator generator;

    public AuthenticatedHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String currentDirectory) {
        request = httpRequest;
        Authenticator auth = new Authenticator(request);
        FileReader reader = new FileReader();
        System.out.println("Is authenticated?" + auth.authenticated());

        if (auth.authenticated()){

            generator.setStatusLine("200");
            generator.setHeaders("Content-Type: text/html");
            generator.setBody(reader.readFile(currentDirectory + request.getPath()));
        }else{
            System.out.println("get's here?????");
            generator.setStatusLine("401");
            generator.setHeaders();
            generator.setBody("Authentication required".getBytes());
        }
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
