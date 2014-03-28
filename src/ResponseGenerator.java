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

    public void setStatusLine(String code){
        Map statusCodes = new LinkedHashMap<String, String>();
        statusCodes.put("200", "200 OK");
        statusCodes.put("206", "206 Partial Content");
        statusCodes.put("404", "404 Not Found");
        statusCodes.put("307", "307 Temporary Redirect");
        statusCodes.put("405", "405 Method Not Allowed");
        statusCodes.put("401", "401 Unauthorized");
        statusLine = "HTTP/1.1 " + statusCodes.get(code) + "\r\n";
    }

    public void setHeaders(String... newHeaders){
        StringBuilder h = new StringBuilder();
        for(String header : newHeaders){
            h.append(header + "\n");
        }
        headers = h + "\r\n";
        fullHeader = (statusLine + headers).getBytes();
    }

    public void setBody(){ //if there is no body
        body = "".getBytes();
    }

    public void setBody(byte[] bodyContent) { // if there is body content
        body = bodyContent;
    }
}
