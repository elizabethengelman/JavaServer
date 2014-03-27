//import java.io.ByteArrayInputStream;
//
///**
// * Created by elizabethengelman on 3/27/14.
// */
//public class IndexHandlerTest {
//    HttpRequest request;
//    Handler handler;
//    String currentDirectory = "../cob_spec/public";
//    ResponseGenerator generator;
//
//    public void setUp(String requestPath){
//        request = new HttpRequest(new ByteArrayInputStream(("GET " + requestPath + " HTTP/1.1").getBytes()));
//        handler = new IndexHandler();
//        generator = new ResponseGenerator();
//    }
//
//    public void testCreateResponse(){
//        handler.createResponse(request, currentDirectory);
//
//    }
//
//}
