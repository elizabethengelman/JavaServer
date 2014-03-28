import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/23/14.
 */
public class DeleteHandler implements Handler {

    HttpRequest request;
    ResponseGenerator generator;
    String currentDirectory;
    OutputStream outputStream;

    public DeleteHandler(){
        generator = new ResponseGenerator();
    }

    public Map<String, byte[]> createResponse() {
        File file;
        Map<String, byte[]> responsePieces = new LinkedHashMap<String, byte[]>();
        if ((file = new File(currentDirectory + request.getPath())).exists()) {
            generator.setStatusLine("200");
            generator.setHeaders("Content-Type: text/plain");
            generator.setBody();
            try{
                PrintWriter writer = new PrintWriter(currentDirectory + request.getPath(), "UTF-8");
                writer.println("");
                writer.close();
            }
            catch(Exception e){
                System.out.println("The file writing exception: " + e);
            }
        }else{
            generator.setStatusLine("404");
            generator.setBody();
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
