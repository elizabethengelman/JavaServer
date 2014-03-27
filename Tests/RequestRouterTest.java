//import org.junit.Test;
//
//import java.io.ByteArrayInputStream;
//
//import static junit.framework.Assert.assertEquals;
//
///**
// * Created by elizabethengelman on 3/20/14.
// */
//public class RequestRouterTest {
//    HttpRequest request;
//    RequestRouter router;
//
//    @Test
//    public void testGetRequest(){
//        setUpRequestAndRouter("GET", "/");
//        Handler testHandler = new GetHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testRedirectRequest(){
//        setUpRequestAndRouter("GET", "/redirect");
//        Handler testHandler = new RedirectHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testParametersRequest(){
//        setUpRequestAndRouter("GET", "/parameters?test=1&test=2");
//        Handler testHandler = new ParametersHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testPartialContentRequest(){
//        setUpRequestAndRouter("GET", "/partial_content.txt");
//        Handler testHandler = new PartialContentHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testMethodOptionsRequest(){
//        setUpRequestAndRouter("GET", "/method_options");
//        Handler testHandler = new MethodOptionsHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testNotFoundRequest(){
//        setUpRequestAndRouter("TESTMETHOD", "/");
//        Handler testHandler = new NotFoundHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testGetLogsRequest(){
//        setUpRequestAndRouter("GET", "/logs");
//        Handler testHandler = new AuthenticatedHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testPutRequest(){
//        setUpRequestAndRouter("PUT", "/");
//        Handler testHandler = new PutHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testPostRequest(){
//        setUpRequestAndRouter("POST", "/");
//        Handler testHandler = new PostHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testDeleteRequest(){
//        setUpRequestAndRouter("DELETE", "/");
//        Handler testHandler = new DeleteHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    @Test
//    public void testOptionsRequest(){
//        setUpRequestAndRouter("OPTIONS", "/");
//        Handler testHandler = new OptionsHandler();
//        assertEquals(testHandler.getClass(), router.routeToHandler().getClass());
//    }
//
//    private void setUpRequestAndRouter(String requestMethod, String requestPath){
//        request = new HttpRequest(new ByteArrayInputStream((requestMethod + " " + requestPath+ " HTTP/1.1").getBytes()));
//        router = new RequestRouter(request);
//        router.routeToHandler();
//    }
//}
//
