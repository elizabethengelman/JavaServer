import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class NotFoundHandlerTest {
    HttpRequest request;
    Handler handler;
    String currentDirectory = "../cob_spec/public";
    OutputStream outputStream;
    String body = "";
    String header = "";

    public void setUp(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("GET " + requestPath + " HTTP/1.1").getBytes()));
        handler = new NotFoundHandler();
        outputStream = new ByteArrayOutputStream();
        header = ("HTTP/1.1 404 Not Found\r\n");
        body = "File not found";
    }

    @Test
    public void testCreateResponse(){
        setUp("/dog");
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        assertTrue(headerOutcome.contains(header));
        assertTrue(body.equals(bodyOutcome));
    }
}
