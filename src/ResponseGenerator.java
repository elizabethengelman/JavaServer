import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class ResponseGenerator {
    byte[] header;
    byte[] body;
    byte[] fullHeader;
    String statusLine;
    String headers = "";

    public void create200StatusWithoutHeaders() {
        setStatusLine("200");
        setHeaders("Content-Type: text/html", "Allow: GET,HEAD,POST,OPTIONS,PUT");
        header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\n\r\n".getBytes();
    }

    public void create200StatusForImage(String imageType) {
        header = ("HTTP/1.1 200 OK\r\nContent-Type: " + imageType + "\n\r\n").getBytes();
    }

    public void create200StatusForTextFile() {
        header = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n".getBytes();
    }

    public void create405Status() {
        header = "HTTP/1.1 405 Method Not Allowed\r\n".getBytes();
    }

    public void create200StatusForOptionsMethod() {
        header = "HTTP/1.1 200 OK\r\n Allow: GET,HEAD,POST,OPTIONS,PUT\r\n".getBytes();
    }

    public void createRedirectStatus() {
        header = "HTTP/1.1 307 Temporary Redirect\r\nLocation: http://localhost:5000/".getBytes();//this needs to be changed-shouldn't be hardcoded
    }

    public void create404Status() {
        header = "HTTP/1.1 404 Not Found\r\n\r\n".getBytes();
    }


    public void setStatusLine(String code){
        Map statusCodes = new LinkedHashMap<String, String>();
        statusCodes.put("200", "200 OK");
        statusCodes.put("206", "206 Partial Content");
        statusCodes.put("404", "404 Not Found");
        statusCodes.put("307", "307 Temporary Redirect");
        statusCodes.put("405", "405 Method Not Allowed");
        statusCodes.put("401", "401 Unauthorized");

        statusLine = "HTTP/1.1 " + statusCodes.get(code) + "\r\n";
        System.out.println(statusLine);
    }

    public void setHeaders(String... newHeaders){
        StringBuilder h = new StringBuilder();
        for(String header : newHeaders){
            h.append(header + "\n");
        }
        headers = h + "\r\n";
//        fullHeader = ("HTTP/1.1 200 OK\r\nContent-Type: image/png" + "\n\r\n").getBytes();
        fullHeader = (statusLine + headers).getBytes();
        System.out.println(statusLine + headers);
    }

    public void setBody(){ //if there is no body
        body = "".getBytes();
    }

    public void setBody(byte[] bodyContent) { // if there is body content
        body = bodyContent;
    }
}
