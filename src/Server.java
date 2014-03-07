import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elizabethengelman on 3/6/14.
 */
public class Server {
    private static String method;
    private static String path;
    private static String httpVersion = "HTTP/1.1";
    private static String statusCode;
    private static String reasonPhrase;
    private static List<String> requestArray = new ArrayList<String>();


    public static void main (String[] args) throws IOException {
        //do i need to set a port number, or just assume it to be on port 80
        int portNumber = 5000;
        String method;
        System.out.println("Server started");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        String fromRequest = " ";
//                    try{
//                while ((fromRequest.length()) != 0){
//                    requestArray.add(fromRequest = input.readLine());
//                    System.out.println(requestArray);
//                }
//                parseRequestMethod(requestArray.get(0));//this should only be done on the first line
//                parsePath(requestArray.get(0));//this should only be done on the first line
//                parseHTTPVersion(requestArray.get(0));//this should only be done on the first line
                output.println(getResponseInitialLine());


//            }
//        catch (IOException e){
//            System.out.println(e.getMessage());
//        }
    }

//    public static String parseRequestMethod(String initialRequestLine){
//        method = parseInitialRequestLine(initialRequestLine, 0);
//        return method;
//    }
//
//    public static String parsePath(String initialRequestLine){
//        path = parseInitialRequestLine(initialRequestLine, 1);
//        return path;
//    }

    public static String parseHTTPVersion(String initialRequestLine){
        httpVersion = parseInitialRequestLine(initialRequestLine, 2);
        return httpVersion;
    }

    private static String parseInitialRequestLine(String initialRequestLine, int elementInLine){
        String element = null;
        String words[] = initialRequestLine.split(" ");
        element = words[elementInLine];
        return element;
    }

    public static String getResponseInitialLine(){
        String response = httpVersion + " 200 OK\r\n";//how do i know if a request is "OK"
        return response;
    }

}
