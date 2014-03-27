import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class MethodOptionsHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;
    OutputStream outputStream;
    String currentDirectory;

    public MethodOptionsHandler(){
        generator = new ResponseGenerator();
    }

    public Map<String, byte[]> createResponse() {
        generator.setStatusLine("200");
        generator.setHeaders("Allow: GET,HEAD,POST,OPTIONS,PUT");
        generator.setBody();
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
}
