import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class PartialContentHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;
    OutputStream outputStream;
    String currentDirectory;
    FileReader reader = new FileReader();

    public PartialContentHandler(){
        generator = new ResponseGenerator();
    }

    public Map<String, byte[]> createResponse() {
        createPartialContentResponse(reader);
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
}
