import java.io.*;

/**
 * Created by elizabethengelman on 3/23/14.
 */
public class DeleteHandler implements Handler {

    HttpRequest request;
    ResponseGenerator generator;

    public DeleteHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String currentDirectory) {
        request = httpRequest;
        File file;
        if ((file = new File(currentDirectory + request.getPath())).exists()) {
            generator.setStatusLine("200");
            generator.setHeaders("Content-Type: text/plain");
            generator.setBody();
            try{
                file.delete();
            }
            catch(Exception e){
                System.out.println("The file writing exception: " + e);
            }
        }else{
            generator.setStatusLine("404");
            generator.setBody();
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
