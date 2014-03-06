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
    public static void main (String[] args) throws IOException {
        //do i need to set a port number, or just assume it to be on port 80
        int portNumber = 5000;
        System.out.println("Server started");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        try{
            while (true){
                String request = input.readLine();
                String response = "this is the response";
                System.out.println(request);
                output.println(response);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String parseRequestMethod(String initialRequestLine){
        return parseInitialRequestLine(initialRequestLine, 0);
    }

    public static String parsePath(String initialRequestLine){
        return parseInitialRequestLine(initialRequestLine, 1);
    }

    public static String parseHTTPVersion(String initialRequestLine){
      return parseInitialRequestLine(initialRequestLine, 2);
    }

    public static String parseInitialRequestLine(String initialRequestLine, int elementInLine){
        String element = null;
        String words[] = initialRequestLine.split(" ");
        element = words[elementInLine];
        return element;
    }
}
