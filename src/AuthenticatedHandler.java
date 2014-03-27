import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class AuthenticatedHandler implements Handler {

    HttpRequest request;
    ResponseGenerator generator;
    String currentDirectory;
    OutputStream outputStream;

    public AuthenticatedHandler(){
        generator = new ResponseGenerator();
    }

    public Map<String, byte[]> createResponse() {
        Map<String, byte[]> responsePieces = new LinkedHashMap<String, byte[]>();
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
}
