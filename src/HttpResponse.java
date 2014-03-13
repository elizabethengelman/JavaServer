import java.io.*;

/**
 * Created by elizabethengelman on 3/12/14.
 */
public class HttpResponse {
    HttpRequest request;
    String requestBody = "";
    public HttpResponse(HttpRequest req){
        request = req;
    }

    public String createResponse(){
        StringBuffer responseReturned = new StringBuffer();
        if (request.getPath().equals("/")){
            responseReturned.append(create200Response());
        }else if(new File("../cob_spec/public" + request.getPath()).exists()){
            if (request.getMethod().equals("GET")){
                responseReturned.append("HTTP/1.1 200 OK\r\n");
                requestBody = createBody();
            }else if (request.getMethod().equals("PUT")){
                responseReturned.append(create405Response());
            }else if (request.getMethod().equals("POST")){
                responseReturned.append(create405Response());
            }
        }else if(request.getPath().equals("/form")) {
            responseReturned.append(create200Response());
        }else if(request.getPath().equals("/method_options")){
            responseReturned.append("HTTP/1.1 200 OK\n Allow: GET,HEAD,POST,OPTIONS,PUT");
        }else{
            responseReturned.append("HTTP/1.1 404 Not Found\r\n");
            requestBody = "File not found.";
        }
        return responseReturned.toString();
    }

    public void sendResponse(String response, String body, PrintWriter outputToClient){
        outputToClient.println(response);
        outputToClient.println(body);
        outputToClient.close();
    }

    private String readFile() throws IOException {
        BufferedReader fileBR = new BufferedReader(new FileReader("../cob_spec/public" + request.getPath()));
        String currentLine;
        StringBuffer fileContent = new StringBuffer();
        while ((currentLine = fileBR.readLine()) != null) {
            fileContent.append(currentLine + '\n');
        }
        return fileContent.toString();
    }

    private String create200Response(){
        return "HTTP/1.1 200 OK \r\n";
    }
    private String create405Response(){
        return "HTTP/1.1 405 Method Not Allowed\r\n";
    }

    private String createBody(){
        String body = new String();
        try{
            body = readFile();
        }catch(IOException e){
            e.printStackTrace();
        }
       return body;
    }
}
