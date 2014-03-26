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
            handler = new GetHandler();
        }else if(request.getMethod().equals("PUT")){
            handler = new PutHandler();
        }else if(request.getMethod().equals("POST")){
            handler = new PostHandler();
        }else if(request.getMethod().equals("DELETE")){
            handler = new DeleteHandler();
        }else if(request.getMethod().equals("OPTIONS")){
            handler = new OptionsHandler();
        }
        else{
            handler = new GetHandler();
        }
        return handler;
    }
}
