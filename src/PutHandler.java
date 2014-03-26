import java.io.*;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class PutHandler implements Handler {
    HttpRequest request;
    ResponseGenerator generator;

    public PutHandler(){
        generator = new ResponseGenerator();
    }

    public void createResponse(HttpRequest httpRequest, String currentDirectory) {
        request = httpRequest;
        if (request.getPath().equals("/")) {
            generator.setStatusLine("200");
            generator.setHeaders();
            generator.setBody();
        }else if(request.getPath().equals("/form")){
            generator.setStatusLine("200");
            generator.setHeaders();
            generator.setBody();
            try{
                PrintWriter writer = new PrintWriter(currentDirectory + request.getPath(), "UTF-8");
                writer.println("data = heathcliff");
                writer.close();
            }
            catch(IOException e){
                System.out.println("The file writing exception: " + e);
            }
        }else if (request.getPath().equals("/file1")) {
            generator.setStatusLine("405");
            generator.setHeaders("Content-Type: text/html");
            generator.setBody();
        }else if (request.getPath().equals("/logs")){
            Authenticator auth = new Authenticator(request);
            generator.setStatusLine("200");
            generator.setHeaders();
            generator.setBody("PUT /these HTTP/1.1".getBytes());
        }else{
            generator.setStatusLine("404");
            generator.setHeaders();
            generator.setBody("File not found".getBytes());
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
