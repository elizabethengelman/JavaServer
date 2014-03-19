import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

public class HttpResponse {
    HttpRequest request;
    byte[] requestBody;
    StringBuffer responseReturned = new StringBuffer();

    public HttpResponse(HttpRequest req) {
        request = req;
    }

    public String createResponse() {
        ResponseGenerator generator = new ResponseGenerator();
        if (request.getPath().equals("/")) {
            responseReturned.append(generator.create200StatusWithoutHeaders());
            requestBody = new byte[0];
        } else if (new File("../cob_spec/public" + request.getPath()).exists()) {
            if (request.getMethod().equals("GET")) {
                FileReader reader = new FileReader();
                if (isAnImage()) {
                    responseReturned.append(generator.create200StatusForImage());
                    requestBody = reader.readFile(request.getPath());
                } else {
                    responseReturned.append(generator.create200StatusForTextFile());
                    requestBody = reader.readFile(request.getPath());
                }
            } else if (request.getMethod().equals("PUT")) {
                responseReturned.append(generator.create405Status());
            } else if (request.getMethod().equals("POST")) {
                responseReturned.append(generator.create405Status());
            }
        } else if (request.getPath().equals("/form")) {
            responseReturned.append(generator.create200StatusWithoutHeaders());
        } else if (request.getPath().equals("/method_options")) {
            responseReturned.append(generator.create200StatusForOptionsMethod());
        } else if (request.getPath().equals("/redirect")) {
            responseReturned.append(generator.createRedirectStatus());
            request.setPath("/");
        } else if (request.getPath().contains("/parameters")) {
            responseReturned.append(generator.create200StatusWithoutHeaders());
            Map<String, String> params = request.getIndividualParams();
            String tempBody = new String();
            Iterator it = params.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pairs = (Map.Entry)it.next();
                tempBody += pairs.getKey() + " = " + pairs.getValue() + "\n";
                it.remove();
            }
            requestBody = tempBody.getBytes();
        } else {
            responseReturned.append(generator.create404Status());
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

//    private String create200ResponseWithNoHeaders() {
//        return "HTTP/1.1 200 OK\r\n\r\n";
//    }
//
//    private String create200ResponseForImage() {
//        return "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n";
//    }
//
//    private String create200ResponseForTextFile(){
//        return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n";
//    }
//
//    private String create405Response() {
//        return "HTTP/1.1 405 Method Not Allowed\r\n";
//    }
//
//    private String create200ResponseForOptionsMethod() {
//        return "HTTP/1.1 200 OK\r\n Allow: GET,HEAD,POST,OPTIONS,PUT\r\n";
//    }
//
//    private String createRedirectResponse() {
//        return "HTTP/1.1 307\r\nLocation: http://localhost:5000/";
//    }
//
//    private String create404Response() {
//        return "HTTP/1.1 404 Not Found\r\n\r\n";
//    }

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