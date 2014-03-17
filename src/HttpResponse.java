import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by elizabethengelman on 3/12/14.
 */
public class HttpResponse {
    HttpRequest request;
    String requestBody = "";
    BufferedImage requestImageBody;
    public HttpResponse(HttpRequest req){
        request = req;
    }

    public String createResponse(){
        StringBuffer responseReturned = new StringBuffer();
        if (request.getPath().equals("/")){
            responseReturned.append(create200Response());
        }else if(new File("../cob_spec/public" + request.getPath()).exists()){
            if (request.getMethod().equals("GET")){
                if (isAnImage()){
                    try{
                        requestImageBody = readImageFile();
                        responseReturned.append("HTTP/1.1 200 OK\r\nAccept-Ranges: bytes\nContent-Type: image/png\nContent-Length: " + getSizeOfImage() + "\r");
                    }
                    catch(IOException e){
                        System.out.println(e);
                    }
                }else{
                      responseReturned.append("HTTP/1.1 200 OK\r\n");
                      requestBody = createBody();
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
            responseReturned.append("HTTP/1.1 307\r\nLocation: /");
            request.setPath("/");
            System.out.println("this is the request: " + request.getPath());
        }
        else if(request.getPath().contains("/parameters")){
            responseReturned.append(create200Response());
            String[] params = request.getIndividualParams();
            for (String param : params){
                requestBody += request.getParameterVariableName(param) + " = " + request.getParameterVariableValue(param) + "\n";
            }
        }

        else{
            responseReturned.append("HTTP/1.1 404 Not Found\r\n");
            requestBody = "File not found.";
        }
        return responseReturned.toString();
    }

    public void sendResponse(String response, String body, PrintWriter outputToClient){
        outputToClient.println(response);
        System.out.println(response);
        outputToClient.println(body);
    }

    public void sendImageResponse(BufferedImage image, OutputStream outputStream){
        try {
//            if (isAPngFile()){
//                ImageIO.write(image, "png", outputStream);
//            }else if (isAJpegFile()){
//                ImageIO.write(image, "jpg", outputStream);
//            }else if (isAGifFile()){
//                ImageIO.write(image, "gif", outputStream);

            ImageIO.write(image, "png", outputStream);

//            }
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

    private BufferedImage readImageFile() throws IOException{
        BufferedImage image = ImageIO.read(new File("../cob_spec/public" + request.getPath()));
        return image;
    }

    private String create200Response(){
        return "HTTP/1.1 200 OK\r\n";
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
    public String getSizeOfImage() throws IOException {
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        ImageIO.write(requestImageBody, "png", tmp);
        tmp.close();
        Integer contentLength = tmp.size();

        return contentLength.toString();
    }

}
