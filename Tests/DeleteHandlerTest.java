import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class DeleteHandlerTest {
    HttpRequest request;
    Handler handler;
    String currentDirectory = "../cob_spec/public";
    OutputStream outputStream;
    String body = "";
    String header = "";

    public void setUp(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("DELETE " + requestPath + " HTTP/1.1").getBytes()));
        handler = new DeleteHandler();
        outputStream = new ByteArrayOutputStream();
        header = ("HTTP/1.1 200 OK\r\nContent-Type: text/plain\n\r\n");
        body = "";
    }

    @Test
    public void testCreateResponse(){
        setUp("/");
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        assertTrue(header.equals(headerOutcome));
        assertTrue(body.equals(bodyOutcome));
    }
}
