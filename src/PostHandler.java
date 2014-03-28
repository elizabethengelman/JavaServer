import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class PostHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;
    OutputStream outputStream;
    String currentDirectory;

    public PostHandler(){
        generator = new ResponseGenerator();
    }

    public Map<String, byte[]> createResponse() {
        if(request.getPath().equals("/form")){
            generator.setStatusLine("200");
            generator.setHeaders("Content-Type: text/plain");
            generator.setBody();
            try{
                PrintWriter writer = new PrintWriter(currentDirectory + request.getPath(), "UTF-8");
                System.out.println("This is the request body that we're POSTING" + request.requestBody);
                writer.println(request.requestBody);
                writer.close();
            }
            catch(IOException e){
                System.out.println("The file writing exception: " + e);
            }
        }else{
            generator.setStatusLine("405");
            generator.setHeaders("Content-Type: text/html");
            generator.setBody();
        }
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
