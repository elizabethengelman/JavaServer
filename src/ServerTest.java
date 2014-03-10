//import org.junit.Test;
//
//import static junit.framework.Assert.assertEquals;
//
///**
// * Created by elizabethengelman on 3/6/14.
// */
//public class ServerTest {
//    String getInitialReqLine = "GET /path/to/file/index.html HTTP/1.0";
//    String postInitialReqLine = "POST /path/to/file/index.html HTTP/1.0";
//    String response = "HTTP/1.0 200 OK";
////    @Test
////    public void parsesGetRequestMethod(){
////        String requestMethod = "GET";
////        assertEquals(requestMethod, Server.parseRequestMethod(getInitialReqLine));
////    }
//
////    @Test
////    public void parsesPostRequestMethod(){
////        String requestMethod = "POST";
////        assertEquals(requestMethod, Server.parseRequestMethod(postInitialReqLine));
////    }
//
////    @Test
////    public void parsesLocalPath(){
////        String localPath = "/path/to/file/index.html";
////        assertEquals(localPath, Server.parsePath(getInitialReqLine));
////    }
//
//    @Test
//    public void parsesHTTPVersion(){
//        String version = "HTTP/1.0";
//        assertEquals(version, Server.parseHTTPVersion(getInitialReqLine));
//    }
//
//    @Test
////    the first piece of the response should be the HTTP version - HTTP/1.0
////    HTTP version + status code + english translation of status code
//    public void initialResponseLine(){
//        String response = "HTTP/1.0 200 OK\r\n";
//        assertEquals(response, Server.getResponseInitialLine());
//    }
//
//    @Test
//    public void requiresHostHeaderFromRequest(){
//
//    }
//
//    @Test
//    public void returnsBadRequestIfNoHostPresent(){
//
//    }
//}
//
