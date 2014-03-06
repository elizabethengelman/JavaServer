import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by elizabethengelman on 3/6/14.
 */
public class Server {
    private static String method;
    private static String path;
    private static String version;
    private static String statusCode;
    private static String reasonPhrase;

    public static void main (String[] args) throws IOException {
        //do i need to set a port number, or just assume it to be on port 80
        int portNumber = 5000;
        String method;
        System.out.println("Server started");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        try{
            while (true){
                String request = input.readLine();
                parseRequestMethod(request);
                parsePath(request);
                parseHTTPVersion(request);

                System.out.println(request);
                output.println(getResponseInitialLine());

            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String parseRequestMethod(String initialRequestLine){
        method = parseInitialRequestLine(initialRequestLine, 0);
        return method;
    }

    public static String parsePath(String initialRequestLine){
        path = parseInitialRequestLine(initialRequestLine, 1);
        return path;
    }

    public static String parseHTTPVersion(String initialRequestLine){
        version = parseInitialRequestLine(initialRequestLine, 2);
        System.out.println(version);
        return version;
    }

    private static String parseInitialRequestLine(String initialRequestLine, int elementInLine){
        String element = null;
        String words[] = initialRequestLine.split(" ");
        element = words[elementInLine];
        return element;
    }

    public static String getResponseInitialLine(){
        String response = version + " 200 OK";
        return response;
    }


}
