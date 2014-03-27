import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class RequestRouter {
    HttpRequest request;
    public RequestRouter(HttpRequest req){
        request = req;
    }
    List<String> cobSpecSpecificPaths = new ArrayList<String>(Arrays.asList("/logs", "/redirect", "/parameters", "/method_options", "/partial_content.txt"));

    public Handler routeToHandler(){
        Handler handler;
        if (cobSpecSpecificPaths.contains(request.getPath()) || request.getPath().contains("parameters")){
            System.out.println("cobspec handler created");
            handler = new CobSpecHandler();
        }else if (request.getMethod().equals("GET")){
            System.out.println("get handler created");
            handler = new GetHandler();
        }else if(request.getMethod().equals("POST")){
            System.out.println("post handler created");
            handler = new PostHandler();
        }else if(request.getMethod().equals("PUT")){
            System.out.println("put handler created");
            handler = new PutHandler();
        }else if(request.getMethod().equals("DELETE")){
            System.out.println("delete handler created");
            handler = new DeleteHandler();
        }else if(request.getMethod().equals("OPTIONS")){
            System.out.println("options handler created");
            handler = new OptionsHandler();
        }else{
            System.out.println("not found handler created");
            handler = new NotFoundHandler();
        }
        System.out.println(handler);
        return handler;
    }
}

