/**
 * Created by elizabethengelman on 3/20/14.
 */
public class RequestRouter {
    HttpRequest request;
    public RequestRouter(HttpRequest req){
        request = req;
    }

    public Handler routeToHandler(){
        Handler handler;
        if (request.getMethod().equals("GET")){
            if (request.getPath().equals("/redirect")){
                handler = new RedirectHandler();
            }else if(request.getPath().contains("/parameters")){
                handler = new ParametersHandler();
            }else if(request.getPath().equals("/partial_content.txt") ){
                handler = new PartialContentHandler();
            }else if(request.getPath().equals("/method_options")){
                handler = new MethodOptionsHandler();
            }else if (request.getPath().equals("/logs")){
                handler = new AuthenticatedHandler();
            }else{
                System.out.print("a get handler is created");
                handler = new GetHandler();
            }
        }else if(request.getMethod().equals("POST")){
            handler = new PostHandler();
        }else if(request.getMethod().equals("PUT")){
            handler = new PutHandler();
        }else if(request.getMethod().equals("DELETE")){
            handler = new DeleteHandler();
        }else if(request.getMethod().equals("OPTIONS")){
            handler = new OptionsHandler();
        }else{
            handler = new NotFoundHandler();
        }
        System.out.println(handler);
        return handler;
    }
}

