/**
 * Created by elizabethengelman on 3/20/14.
 */
public class RequestRouter {
    Handler[] handlers;

    HttpRequest request;
    public RequestRouter(HttpRequest req){
        request = req;
        handlers = new Handler[]{new GetHandler(), new PutHandler(), new PostHandler(), new DeleteHandler(), new OptionsHandler(), new NotFoundHandler()};
    }
    //this is a factory pattern - it creates a new handler based on criteria!
    //try to get all of the confusing/messy if/elses in here, rather than in the handlers
    //themselves. i.e. create handlers for each new idea
    public Handler routeToHandler(){
        Handler handler = null;
        for (Handler h : handlers){
            if (h.canHandleRequest(request)){
                handler = h;
                break;
            }
        }

//        Handler[] handlerArray = {new GetHandler(), new PutHandler(), new PostHandler(), new DeleteHandler(), new OptionsHandler()};
//
//        if (request.getMethod().equals("GET")){
//            handler = new GetHandler();
//        }else if(request.getMethod().equals("PUT")){
//            handler = new PutHandler();
//        }else if(request.getMethod().equals("POST")){
//            handler = new PostHandler();
//        }else if(request.getMethod().equals("DELETE")){
//            handler = new DeleteHandler();
//        }else if(request.getMethod().equals("OPTIONS")){
//            handler = new OptionsHandler();
//        }
//        else{
//            handler = new NotFoundHandler();
//        }
        return handler;
    }
}


