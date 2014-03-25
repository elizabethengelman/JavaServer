import java.io.*;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class PostHandler implements Handler{
    HttpRequest request;
    ResponseGenerator generator;

    public PostHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String currentDirectory) {
        request = httpRequest;
        if (request.getPath().equals("/")) {
            generator.create200StatusWithoutHeaders();
            generator.setBody();
        }else if(request.getPath().equals("/form")){
            generator.create200StatusWithoutHeaders();
            generator.setBody();
            try{
                PrintWriter writer = new PrintWriter(currentDirectory + request.getPath(), "UTF-8");
                writer.println("data = cosby");
                writer.close();
            }
            catch(IOException e){
                System.out.println("The file writing exception: " + e);
            }
        }else if (new File(currentDirectory + request.getPath()).exists()) {
            generator.create405Status();
            generator.setBody();
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
