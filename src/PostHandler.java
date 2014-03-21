import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class PostHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;

    public PostHandler(HttpRequest req){
        request = req;
        generator = new ResponseGenerator();
    }

    public void createResponse() {
        if (request.getPath().equals("/")) {
            generator.create200StatusWithoutHeaders();
            generator.setBody();
        }else if (new File("../cob_spec/public" + request.getPath()).exists()) {
            generator.create405Status();
            generator.setBody();
        }else if(request.getPath().equals("/form")){
            generator.create200StatusWithoutHeaders();
            generator.setBody();
        }
        else{
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
