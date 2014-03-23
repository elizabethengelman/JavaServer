import java.io.*;

/**
 * Created by elizabethengelman on 3/23/14.
 */
public class DeleteHandler implements Handler {

    HttpRequest request;
    ResponseGenerator generator;

    public DeleteHandler(HttpRequest req){
        request = req;
        generator = new ResponseGenerator();
    }

    public void createResponse() {
        File file;
        if ((file = new File("../cob_spec/public" + request.getPath())).exists()) {
            generator.create200StatusWithoutHeaders();
            generator.setBody();
            try{
                file.delete();
            }
            catch(Exception e){
                System.out.println("The file writing exception: " + e);
            }
        }else{
            generator.create404Status();
            generator.setBody();
        }
    }

    public void sendResponse(OutputStream outputStream) {
        try {
            byte[] requestHeader = generator.header;
            byte[] requestBody = generator.body;
            DataOutputStream dOut = new DataOutputStream(outputStream);
            dOut.write(requestHeader);
            dOut.write(requestBody);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
