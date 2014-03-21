import org.junit.Test;

import java.io.ByteArrayInputStream;

import static junit.framework.Assert.assertEquals;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class RequestRouterTest {
    HttpRequest request;
    RequestRouter router;

    @Test
    public void testGetRequest(){
        setUpRequestAndRouter("GET");
        Handler testGetHandler = new GetHandler(request);
        assertEquals(testGetHandler.getClass(), router.routeToHandler().getClass());
    }

    @Test
    public void testPutRequest(){
        setUpRequestAndRouter("PUT");
        Handler testPutHandler = new PutHandler(request);
        assertEquals(testPutHandler.getClass(), router.routeToHandler().getClass());
    }

    @Test
    public void testPostRequest(){
        setUpRequestAndRouter("POST");
        Handler testPostHandler = new PostHandler(request);
        assertEquals(testPostHandler.getClass(), router.routeToHandler().getClass());

    }

    private void setUpRequestAndRouter(String requestMethod){
        request = new HttpRequest(new ByteArrayInputStream((requestMethod + " / HTTP/1.1").getBytes()));
        router = new RequestRouter(request);
        router.routeToHandler();
    }
}

