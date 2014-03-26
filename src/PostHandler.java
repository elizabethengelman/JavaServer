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
            generator.setStatusLine("200");
            generator.setBody();
        }else if(request.getPath().equals("/form")){
            generator.setStatusLine("200");
            generator.setHeaders("Content-Type: text/plain");
            generator.setBody();
            try{
                System.out.println("this is the current file: " + currentDirectory + request.getPath());
                PrintWriter writer = new PrintWriter(currentDirectory + request.getPath(), "UTF-8");

                writer.println("data = cosby");
                writer.close();
            }
            catch(IOException e){
                System.out.println("The file writing exception: " + e);
            }
        }else if(request.getPath().equals("/text-file.txt")){

            generator.setStatusLine("405");
            generator.setHeaders("Content-Type: text/html");
            generator.setBody();
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
