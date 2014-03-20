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
            handler = new GetHandler(request);
        }else if(request.getMethod().equals("PUT")){
            handler = new PutHandler(request);
        }else if(request.getMethod().equals("POST")){
            handler = new PostHandler(request);
        }else{
            //send to otherHandler
            handler = new GetHandler(request); // change to Other handler??????
        }
        return handler;
    }
}
