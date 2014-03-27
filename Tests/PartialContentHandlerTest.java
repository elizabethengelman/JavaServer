import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class PartialContentHandlerTest {
    HttpRequest request;
    Handler handler;
    String currentDirectory = "../cob_spec/public";
    OutputStream outputStream;
    String body = "";
    String header = "";

    public void setUp(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("GET " + requestPath + " HTTP/1.1").getBytes()));
        handler = new PartialContentHandler();
        outputStream = new ByteArrayOutputStream();
        header = ("HTTP/1.1 206 Partial Content\r\nContent-Type: text/plain\nContent-Range: bytes /77\n");
        body = "This is a file that contains text to read part of in order to fulfill a 206.\n";
    }

    @Test
    public void testCreateResponse(){
        setUp("/partial_content.txt");
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        assertTrue(headerOutcome.contains(header));
        assertTrue(body.equals(bodyOutcome));
    }
}
