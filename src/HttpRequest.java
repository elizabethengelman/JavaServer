import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpRequest {
    InputStream inputStream;
    BufferedReader inputFromClient;
    String requestString;

    public HttpRequest(InputStream is){
        inputStream = is;
        inputFromClient = new BufferedReader(new InputStreamReader(inputStream));
        try{
            requestString = inputFromClient.readLine();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public String getMethod(){
        return requestString.split(" ")[0];
    }

    public String getPath(){
        return requestString.split(" ")[1];
    }

    public String getHttpVersion(){
        return requestString.split(" ")[2];
    }
}
