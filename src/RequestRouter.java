/**
 * Created by elizabethengelman on 3/20/14.
 */
public class RequestRouter {
    HttpRequest request;
    public RequestRouter(HttpRequest req){
        request = req;
    }

    public void routeToHandler(){
        if (request.getMethod().equals("GET")){
            //send to getHandler

        }else if(request.getMethod().equals("PUT")){
            //send to putHandler
        }else if(request.getMethod().equals("POST")){
            // send to postHandler
        }else{
            //send to otherHandler
        }
    }

}
