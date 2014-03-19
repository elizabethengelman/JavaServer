import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/12/14.
 */
public class HttpResponse {
    HttpRequest request;
    byte[] requestBody;
    StringBuffer responseReturned = new StringBuffer();

    public HttpResponse(HttpRequest req) {
        request = req;
    }

    public String createResponse() {

        if (request.getPath().equals("/")) {
            responseReturned.append(create200Response());
            requestBody = new byte[0];
        } else if (new File("../cob_spec/public" + request.getPath()).exists()) {
            if (request.getMethod().equals("GET")) {
                if (isAnImage()) {
                    responseReturned.append("HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n");
                    setBodyContent();
                } else {
                    responseReturned.append("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n");
                    setBodyContent();
                }

            } else if (request.getMethod().equals("PUT")) {
                responseReturned.append(create405Response());
            } else if (request.getMethod().equals("POST")) {
                responseReturned.append(create405Response());
            }
        } else if (request.getPath().equals("/form")) {
            responseReturned.append(create200Response());
        } else if (request.getPath().equals("/method_options")) {
            responseReturned.append("HTTP/1.1 200 OK\r\n Allow: GET,HEAD,POST,OPTIONS,PUT\r\n");
        } else if (request.getPath().equals("/redirect")) {
            responseReturned.append("HTTP/1.1 307\r\nLocation: http://localhost:5000/");
            request.setPath("/");
        } else if (request.getPath().contains("/parameters")) {
            responseReturned.append(create200Response());
            Map<String, String> params = request.getIndividualParams();
            String tempBody = new String();
            Iterator it = params.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pairs = (Map.Entry)it.next();
                tempBody += pairs.getKey() + " = " + pairs.getValue() + "\n";
                it.remove();
            }



            System.out.println("this is tempBody: " + tempBody);
            requestBody = tempBody.getBytes();
        } else {
            responseReturned.append("HTTP/1.1 404 Not Found\r\n\r\n");
            requestBody = ("File not found.").getBytes();
        }
        return responseReturned.toString();
    }

    public void sendNewResponse(OutputStream outputStream) {
        try {
            byte[] requestHeader = responseReturned.toString().getBytes();
            DataOutputStream dOut = new DataOutputStream(outputStream);
            dOut.write(requestHeader);
            dOut.write(requestBody);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private byte[] readFile() throws IOException {
        Path path = Paths.get("../cob_spec/public" + request.getPath());
        byte[] imageFileData = Files.readAllBytes(path);
        return imageFileData;
    }

    private String create200Response() {
        return "HTTP/1.1 200 OK\r\n\r\n";
    }

    private String create405Response() {
        return "HTTP/1.1 405 Method Not Allowed\r\n";
    }

    private void setBodyContent() {
        try {
            requestBody = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean isAGifFile() {
        Boolean outcome = false;
        if (request.getPath().contains("gif")) {
            outcome = true;
        }
        return outcome;
    }

    private Boolean isAJpegFile() {
        Boolean outcome = false;
        if (request.getPath().contains("jpeg")) {
            outcome = true;
        }
        if (request.getPath().contains("jpg")) {
            outcome = true;
        }
        return outcome;
    }

    private Boolean isAPngFile() {
        Boolean outcome = false;
        if (request.getPath().contains("png")) {
            outcome = true;
        }
        return outcome;
    }

    public Boolean isAnImage() {
        if (isAGifFile() || isAJpegFile() || isAPngFile()) {
            return true;
        } else {
            return false;
        }
    }
}