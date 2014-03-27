import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class ParametersHandler implements Handler {
    HttpRequest request;
    ResponseGenerator generator;

    public ParametersHandler(){
        generator = new ResponseGenerator();
    }
    public void createResponse(HttpRequest httpRequest, String currentDirectory) {
        request = httpRequest;
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
        generator.setBody(iterateThroughParameters().getBytes());
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

    private String iterateThroughParameters(){
        Map<String, String> params = request.getIndividualParams();
        String tempBody = new String();
        Iterator it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
            tempBody += pairs.getKey() + " = " + pairs.getValue() + "\n";
            it.remove();
        }
        return tempBody;
    }
}
