import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class IndexHandlerTest {
    HttpRequest request;
    Handler handler;
    String currentDirectory = "../cob_spec/public";
    OutputStream outputStream;
    String fullHeader = "";
    String body = "";

    public void setUp(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("GET " + requestPath + " HTTP/1.1").getBytes()));
        handler = new IndexHandler();
        outputStream = new ByteArrayOutputStream();
        DirectoryBuilder db = new DirectoryBuilder(currentDirectory, "/");
        fullHeader = ("HTTP/1.1 200 OK\r\nContent-Type: text/html\n\r\n");
        body = db.getLinksOfFiles();

    }

    @Test
    public void testCreateResponse(){
        setUp("/");
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        assertTrue(fullHeader.equals(headerOutcome));
        assertTrue(body.equals(bodyOutcome));
    }
}
