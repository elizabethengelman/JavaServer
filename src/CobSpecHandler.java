import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/26/14.
 */
public class CobSpecHandler implements Handler {
    HttpRequest request;
    ResponseGenerator generator;
    String currentDirectory;

    public CobSpecHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String directory) {
        request = httpRequest;
        currentDirectory = directory;
        FileReader reader = new FileReader();
        if(request.getPath().equals("/redirect")){
            System.out.println("in the redirection");
            createRedirectResponse();
        }else if(request.getPath().equals("/partial_content.txt")){
            if (request.getMethod() == "PUT" || request.getMethod() == "POST"){
                generator.setStatusLine("405");
                generator.setHeaders();
                generator.setBody("Method not allowed".getBytes());
            }else{
                createPartialContentResponse(reader);
            }
        }else if(request.getPath().equals("/method_options")){
            createMethodOptionsResponse();
        }else if(request.getPath().contains("/parameters")){
            createParameterResponse();
        }else if(request.getPath().equals("/logs")) {
            Authenticator auth = new Authenticator(request);
            if (auth.authenticated()){
                generator.setStatusLine("200");
                generator.setHeaders("Content-Type: text/html");
                generator.setBody(reader.readFile(currentDirectory + request.getPath()));

            }else{
                generator.setStatusLine("401");
                generator.setHeaders();
                generator.setBody("Authentication required".getBytes());
            }
        }else{
            generator.setStatusLine("404");
            generator.setHeaders();
            generator.setBody("File not found".getBytes());
        }
    }

    private void createParameterResponse() {
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
        generator.setBody(iterateThroughParameters().getBytes());
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

    private void createMethodOptionsResponse() {
        generator.setStatusLine("200");
        generator.setHeaders("Allow: GET,HEAD,POST,OPTIONS,PUT");
        generator.setBody();
    }

    private void createRedirectResponse() {
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
