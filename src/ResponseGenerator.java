/**
 * Created by elizabethengelman on 3/20/14.
 */
public class ResponseGenerator {
    byte[] header;
    byte[] body;
    byte[] request;

    public void create200StatusWithoutHeaders() {
        header = "HTTP/1.1 200 OK\r\n\r\n".getBytes();
    }

    public void create200StatusForImage() {
        header = "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n".getBytes();
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
        header = "HTTP/1.1 307\r\nLocation: http://localhost:5000/".getBytes();
    }

    public void create404Status() {
        header = "HTTP/1.1 404 Not Found\r\n\r\n".getBytes();
    }

    public void setBody(){ //if there is no body
        body = null;
    }

    public void setBody(byte[] bodyContent) { // if there is body content
        body = bodyContent;
    }



}

