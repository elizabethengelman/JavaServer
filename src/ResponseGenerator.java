/**
 * Created by elizabethengelman on 3/19/14.
 */
public class ResponseGenerator {
    public String create200StatusWithoutHeaders(){
        return "HTTP/1.1 200 OK\r\n\r\n";
    }

    public String create200StatusForImage() {
        return "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n";
    }

    public String create200StatusForTextFile(){
        return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n";
    }

    public String create405Status() {
        return "HTTP/1.1 405 Method Not Allowed\r\n";
    }

    public String create200StatusForOptionsMethod() {
        return "HTTP/1.1 200 OK\r\n Allow: GET,HEAD,POST,OPTIONS,PUT\r\n";
    }

    public String createRedirectStatus() {
        return "HTTP/1.1 307\r\nLocation: http://localhost:5000/";
    }

    public String create404Status() {
        return "HTTP/1.1 404 Not Found\r\n\r\n";
    }

}
