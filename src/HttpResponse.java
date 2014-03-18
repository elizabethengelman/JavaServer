import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by elizabethengelman on 3/12/14.
 */
public class HttpResponse {
    HttpRequest request;
    byte[] requestBody;
    byte[] requestImageBody;
    StringBuffer responseReturned = new StringBuffer();
    public HttpResponse(HttpRequest req){
        request = req;
    }

    public String createResponse(){

        if (request.getPath().equals("/")){
            responseReturned.append(create200Response());
            requestBody = new byte[0];
        }else if(new File("../cob_spec/public" + request.getPath()).exists()){
            if (request.getMethod().equals("GET")){
                if (isAnImage()){
                    try{
                        requestBody = readImageFile();
                        responseReturned.append("HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n");
                    }
                    catch(IOException e){
                        System.out.println(e);
                    }
                }else{
                      responseReturned.append("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
                      setBody();
                }

            }else if (request.getMethod().equals("PUT")){
                responseReturned.append(create405Response());
            }else if (request.getMethod().equals("POST")){
                responseReturned.append(create405Response());
            }
        }else if(request.getPath().equals("/form")) {
            responseReturned.append(create200Response());
        }else if(request.getPath().equals("/method_options")){
            responseReturned.append("HTTP/1.1 200 OK\r\n Allow: GET,HEAD,POST,OPTIONS,PUT\r\n");
        }else if(request.getPath().equals("/redirect")){
            responseReturned.append("HTTP/1.1 307\r\nLocation: http://localhost:5000/");
            request.setPath("/");
        }
//        else if(request.getPath().contains("/parameters")){
//            responseReturned.append(create200Response());
//            String[] params = request.getIndividualParams();
//            for (String param : params){
//                requestBody += request.getParameterVariableName(param) + " = " + request.getParameterVariableValue(param) + "\n";
//            }
//        }

        else{
            responseReturned.append("HTTP/1.1 404 Not Found\r\n");
//            requestBody = "File not found.";
        }
        return responseReturned.toString();
    }

    public void sendResponse(String response, String body, PrintWriter outputToClient){
        outputToClient.println(response);
        outputToClient.println(body);
    }

    public void sendNewResponse(OutputStream outputStream){
        try {
            byte[] requestHeader = responseReturned.toString().getBytes();
//            if (isAPngFile()){
//                ImageIO.write(image, "png", outputStream);
//            }else if (isAJpegFile()){
//                ImageIO.write(image, "jpg", outputStream);
//            }else if (isAGifFile()){
//                ImageIO.write(image, "gif", outputStream);
            DataOutputStream dOut = new DataOutputStream(outputStream);
            dOut.write(requestHeader);
            dOut.write(requestBody);
        }
        catch(IOException e){
            System.out.println(e);
        }
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

    private byte[] readImageFile() throws IOException{
//        BufferedImage image = ImageIO.read(new File("../cob_spec/public" + request.getPath()));
        Path path = Paths.get("../cob_spec/public" + request.getPath());
        byte[] imageFileData = Files.readAllBytes(path);
        return imageFileData;
    }

    private String create200Response(){
        return "HTTP/1.1 200 OK\r\n\r\n";
    }

    private String create405Response(){
        return "HTTP/1.1 405 Method Not Allowed\r\n";
    }

    private void setBody(){
        try{
            requestBody = readImageFile();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private Boolean isAGifFile(){
        Boolean outcome = false;
        if (request.getPath().contains("gif")){
            outcome = true;
        }
        return outcome;
    }

    private Boolean isAJpegFile(){
        Boolean outcome = false;
        if (request.getPath().contains("jpeg")){
            outcome = true;
        }if (request.getPath().contains("jpg")){
            outcome = true;
        }
        return outcome;
    }

    private Boolean isAPngFile(){
        Boolean outcome = false;
        if (request.getPath().contains("png")){
            outcome = true;
        }
        return outcome;
    }

    public Boolean isAnImage() {
        if (isAGifFile() || isAJpegFile() || isAPngFile()){
            return true;
        }else{
            return false;
            }
    }
//    public String getSizeOfImage() throws IOException {
//        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
//        ImageIO.write(requestImageBody, "png", tmp);
//        tmp.close();
//        Integer contentLength = tmp.size();
//
//        return contentLength.toString();
//    }
}
