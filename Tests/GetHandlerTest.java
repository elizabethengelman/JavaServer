import org.junit.Test;

import java.io.ByteArrayInputStream;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class GetHandlerTest {
    HttpRequest request;
    Handler handler;

    private void setUpRequestAndRouter(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("GET " + requestPath + " HTTP/1.1").getBytes()));
        handler = new GetHandler();
//        handler.createResponse();
    }

    @Test
    public void testResponseForIndex(){
        setUpRequestAndRouter("/");
    }

    @Test
    public void testResponseForTextFile(){

    }

    @Test
    public void testResponseForImageFile(){

    }

    @Test
    public void testResponseForRedirect(){

    }

    @Test
    public void testResponseForParameters(){

    }

    @Test
    public void testResponseForMethodOptions(){

    }

    @Test
    public void testResponseFor404(){

    }
}
